package joao.io.projetoRest.service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import joao.io.projetoRest.exception.HospedeException;
import joao.io.projetoRest.model.Hospedagem;
import joao.io.projetoRest.model.Hospede;
import joao.io.projetoRest.repository.HospedagemRepository;
import joao.io.projetoRest.repository.HospedeRepository;

@Service
public class HospedagemService {

	@Autowired
	private HospedeRepository hospedeRepository;
	
	@Autowired
	private HospedagemRepository hospedagemRepository;
	
	@Autowired
    private MessageSource messageSource;
	
	public void salvarHospedagem(Hospedagem hospedagem) throws HospedeException{
		try {
			hospedagemRepository.save(hospedagem);
		}catch(Exception e) {
			e.printStackTrace();
			throw new HospedeException(String.format(messageSource.getMessage("hospedagem.erro.salvamento", null, Locale.getDefault()), "hospedagem.getNome()"),e);
		}
	}
	
	public void removerHospedagem(Long id) throws HospedeException{
		try {
			Hospedagem hospedagem = buscarHospedagemPorId(id);
			hospedagemRepository.delete(hospedagem);
		}catch(HospedeException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new HospedeException(String.format(messageSource.getMessage("hospedagem.erro.remocao", null, Locale.getDefault()), id),e);
		}
	}
	
	public Hospedagem buscarHospedagemPorId(Long id) throws HospedeException{
		try {
			Optional<Hospedagem> hospedagem = hospedagemRepository.findById(id);

			//Aplica a Regra de Negócio
			hospedagem.get().setValorTotal(aplicarRegra(hospedagem.get()));
			
			if (hospedagem.isPresent()) {
				return hospedagem.get();
			}else {
				throw new HospedeException(String.format(messageSource.getMessage("hospedagem.erro.hospedagem.naoencontrado", null, Locale.getDefault()), id), null);
			}
		}catch(HospedeException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new HospedeException(String.format(messageSource.getMessage("hospedagem.erro.consulta", null, Locale.getDefault()), id),e);
		}
	}
	
	public List<Hospedagem> buscarTodos() throws HospedeException{
		try {
			List<Hospedagem> lstHospedagem = new ArrayList<Hospedagem>();
			Iterable<Hospedagem> it = hospedagemRepository.findAll();
			for (Hospedagem hospedagem: it) {
				Optional<Hospede> h = hospedeRepository.findById(hospedagem.getHospede().getId());
				hospedagem.setHospede(new Hospede(h.get()));
				
				//Aplica a Regra de Negócio
				hospedagem.setValorTotal(aplicarRegra(hospedagem));
				lstHospedagem.add(hospedagem);
			}
			
			if (lstHospedagem.isEmpty()) {
				throw new HospedeException(messageSource.getMessage("Hospedagem.erro.nenhum.encontrado", null, Locale.getDefault()),null);
			}
			return lstHospedagem;
		}catch(HospedeException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new HospedeException(messageSource.getMessage("hospedagem.erro.nenhum.encontrado", null, Locale.getDefault()),e);
		}
	}
	
	private double aplicarRegra(Hospedagem hospedagem) {
		double valor = 0.0;

		List<LocalDate> listaDosospedados = new ArrayList<>();
		Date dateEntrada = hospedagem.getDataEntrada();
		Date dateSaida = hospedagem.getDataSaida();

		LocalDate entrada = dateEntrada.toInstant().atZone(ZoneId.systemDefault() ).toLocalDate();
		LocalDate saida = dateSaida.toInstant().atZone(ZoneId.systemDefault() ).toLocalDate();

		int diaInicio = entrada.getDayOfMonth();
		int anoInicio = entrada.getYear();
		int mesInicio = entrada.getMonthValue();
		int diaFim = saida.getDayOfMonth();
		int mesFim = saida.getMonthValue();

		do{
			YearMonth anoMes = YearMonth.of(anoInicio, mesInicio);
			int diasFinal = anoMes.lengthOfMonth();

			if(mesInicio == mesFim) {
				diasFinal = diaFim;
			}

			for (int dia = diaInicio ; dia <= diasFinal; dia++) {
				LocalDate data = anoMes.atDay(dia);

				listaDosospedados.add(data);
			}

			++mesInicio;
			diaInicio = 1;
		}while(mesInicio <= mesFim);

		for (int i = 0; i < listaDosospedados.size(); i++) {
			LocalDate data = listaDosospedados.get(i);

			//1º Regra - Uma diária no hotel de segunda à sexta custa R$120,00;
			if (data.getDayOfWeek() != DayOfWeek.SATURDAY
					&& data.getDayOfWeek() != DayOfWeek.SUNDAY) {
				valor = valor + 120.00;
			}else {
				//2º Regra - Uma diária no hotel em finais de semana custa R$150,00;
				valor = valor + 150.00;
			}

			//3º Regra - Caso a pessoa precise de uma vaga na garagem do hotel há um acréscimo diário, sendo R$15,00 de segunda à sexta e R$20,00 nos finais de semana;
			if ((data.getDayOfWeek() == DayOfWeek.SATURDAY
					|| data.getDayOfWeek() == DayOfWeek.SUNDAY ) 
					&& hospedagem.isAdicionarVericulo()) {
				valor = valor + 20.00;
			}else if(data.getDayOfWeek() != DayOfWeek.SATURDAY
					&& data.getDayOfWeek() != DayOfWeek.SUNDAY 
					&& hospedagem.isAdicionarVericulo()) {
				//Dia de semana
				valor = valor + 15.00;
			}

			//4º Regra - Caso o horário da saída seja após às 16:30h deve ser cobrada uma diária extra;
			SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss" );
			Calendar horarioExedidoNaSaida = Calendar.getInstance();
			horarioExedidoNaSaida.set(Calendar.HOUR_OF_DAY, 16);
			horarioExedidoNaSaida.set(Calendar.MINUTE, 30);
			horarioExedidoNaSaida.set(Calendar.SECOND, 00);

			Calendar horarioSaida = Calendar.getInstance();
			horarioSaida.set(Calendar.HOUR_OF_DAY, dateSaida.getHours());
			horarioSaida.set(Calendar.MINUTE, dateSaida.getMinutes());
			horarioSaida.set(Calendar.SECOND, 00);

			if(horarioSaida.getTime().compareTo(horarioExedidoNaSaida.getTime()) >= 0) {
				valor = valor + 120.00;
			}
		}
		return valor;
	}
}
