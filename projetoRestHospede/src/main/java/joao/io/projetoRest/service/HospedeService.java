package joao.io.projetoRest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import joao.io.projetoRest.exception.HospedeException;
import joao.io.projetoRest.model.Hospede;
import joao.io.projetoRest.repository.HospedeRepository;

@Service
public class HospedeService {
	
	@Autowired
	private HospedeRepository hospedeRepository;
	
	@Autowired
    private MessageSource messageSource;
	
	public void salvarHospede(Hospede hospede) throws HospedeException{
		try {
			hospedeRepository.save(hospede);
		}catch(Exception e) {
			e.printStackTrace();
			throw new HospedeException(String.format(messageSource.getMessage("Hospede.erro.salvamento", null, Locale.getDefault()), "hospede.getNome()"),e);
		}
	}
	
	public void removerHospede(Long id) throws HospedeException{
		try {
			Hospede Hospede = buscarHospedePorId(id);
			hospedeRepository.delete(Hospede);
		}catch(HospedeException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new HospedeException(String.format(messageSource.getMessage("Hospede.erro.remocao", null, Locale.getDefault()), id),e);
		}
	}
	
	public Hospede buscarHospedePorId(Long id) throws HospedeException{
		try {
			Optional<Hospede> hospede = hospedeRepository.findById(id);
			if (hospede.isPresent()) {
				return hospede.get();
			}else {
				throw new HospedeException(String.format(messageSource.getMessage("Hospede.erro.Hospede.naoencontrado", null, Locale.getDefault()), id), null);
			}
		}catch(HospedeException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new HospedeException(String.format(messageSource.getMessage("Hospede.erro.consulta", null, Locale.getDefault()), id),e);
		}
	}
	
	public List<Hospede> buscarTodos() throws HospedeException{
		try {
			List<Hospede> lstHospede = new ArrayList<Hospede>();
			Iterable<Hospede> it = hospedeRepository.findAll();
			for (Hospede Hospede: it) {
				lstHospede.add(Hospede);
			}
			if (lstHospede.isEmpty()) {
				throw new HospedeException(messageSource.getMessage("Hospede.erro.nenhum.encontrado", null, Locale.getDefault()),null);
			}
			return lstHospede;
		}catch(HospedeException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw new HospedeException(messageSource.getMessage("Hospede.erro.nenhum.encontrado", null, Locale.getDefault()),e);
		}
	}
	
}
