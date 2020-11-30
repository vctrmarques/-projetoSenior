package joao.io.projetoRest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import joao.io.projetoRest.exception.HospedeException;
import joao.io.projetoRest.model.Hospedagem;
import joao.io.projetoRest.model.Hospede;
import joao.io.projetoRest.rest.CheckIn;
import joao.io.projetoRest.rest.Resposta;
import joao.io.projetoRest.service.HospedagemService;
import joao.io.projetoRest.service.HospedeService;
import joao.io.projetoRest.util.Constantes;

@RestController
public class RestHospedagemController {
	
	@Autowired
	private HospedeService hospedeService;

	@Autowired
	private HospedagemService hospedagemService;
	
	Logger logger = LoggerFactory.getLogger(RestHospedagemController.class);
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDAGEM, method = RequestMethod.GET)
	public @ResponseBody Resposta consultarTodos() {
		Resposta resposta = new Resposta();
		try {
			resposta.setResposta(hospedagemService.buscarTodos());
			logger.info("Consultando todas as hospedagem");
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDAGEM + "/{id}", method = RequestMethod.GET)
	public @ResponseBody CheckIn consultarhospedagemPorId(@PathVariable Long id) {
		CheckIn checkIn = new CheckIn();
		try {
			Hospedagem hospedagem = hospedagemService.buscarHospedagemPorId(id);
			Hospede hospede = hospedeService.buscarHospedePorId(hospedagem.getHospede().getId());
			hospedagem.setHospede(new Hospede(hospede));
			
			checkIn.setCheckIn(hospedagem); 
			logger.info(String.format("Consultando hospedagem %s", id));
		}catch(HospedeException e) {
			checkIn.setCodigo(Constantes.Status.CODIGO_ERRO);
			checkIn.setMensagem(e.getMensagem());
		}
		return checkIn;
	}
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDAGEM, method = RequestMethod.POST)
	public @ResponseBody Resposta salvarhospedagem(@RequestBody Hospedagem hospedagem) {
		Resposta resposta = new Resposta();
		try {
			hospedagemService.salvarHospedagem(hospedagem);
			resposta.setResposta(hospedagem); 
			logger.info(String.format("Salvando nova hospedagem %s", hospedagem.getHospede().getNome()));
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDAGEM + "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Resposta atualizarhospedagem(@RequestBody Hospedagem hospedagemAtualizado, @PathVariable Long id) {
		Resposta resposta = new Resposta();
		try {
			Hospedagem hospedagemSalvo =  hospedagemService.buscarHospedagemPorId(id);
			Hospede hospede = hospedeService.buscarHospedePorId(hospedagemSalvo.getHospede().getId());
			hospedagemSalvo.setHospede(new Hospede(hospede));
			hospedagemSalvo.setId(hospedagemSalvo.getId());
			hospedagemSalvo.setDataEntrada(hospedagemAtualizado.getDataEntrada());
			hospedagemSalvo.setDataSaida(hospedagemAtualizado.getDataSaida());
			hospedagemSalvo.setAdicionarVericulo(hospedagemAtualizado.isAdicionarVericulo());
			
			
			hospedagemService.salvarHospedagem(hospedagemSalvo);
			resposta.setResposta(hospedagemSalvo);
			logger.info(String.format("Atualizando hospedagem %s", id));
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDAGEM + "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Resposta removerhospedagem(@PathVariable Long id) {
		Resposta resposta = new Resposta();
		try {
			hospedagemService.removerHospedagem(id);
			logger.info(String.format("Removendo hospedagem %s", id));
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
}
