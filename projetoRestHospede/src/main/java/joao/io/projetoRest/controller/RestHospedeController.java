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
import joao.io.projetoRest.model.Hospede;
import joao.io.projetoRest.rest.Resposta;
import joao.io.projetoRest.service.HospedeService;
import joao.io.projetoRest.util.Constantes;

@RestController
public class RestHospedeController {
	
	@Autowired
	private HospedeService hospedeService;
	
	private static final Logger logger = LoggerFactory.getLogger(RestHospedeController.class);
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDE, method = RequestMethod.GET)
	public @ResponseBody Resposta consultarTodos() {
		Resposta resposta = new Resposta();
		try {
			resposta.setResposta(hospedeService.buscarTodos());
			logger.info("Consultando todos os hospedes");
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDE + "/{id}", method = RequestMethod.GET)
	public @ResponseBody Resposta consultarhospedePorId(@PathVariable Long id) {
		Resposta resposta = new Resposta();
		try {
			Hospede hospede = hospedeService.buscarHospedePorId(id);
			resposta.setResposta(hospede); 
			logger.info(String.format("Consultando hospede %s", id));
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDE, method = RequestMethod.POST)
	public @ResponseBody Resposta salvarhospede(@RequestBody Hospede hospede) {
		Resposta resposta = new Resposta();
		try {
			hospedeService.salvarHospede(hospede);
			resposta.setResposta(hospede); 
			logger.info(String.format("Salvando novo hospede %s", hospede.getNome()));
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDE + "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Resposta atualizarhospede(@RequestBody Hospede hospedeAtualizado, @PathVariable Long id) {
		Resposta resposta = new Resposta();
		try {
			Hospede HospedeSalvo =  hospedeService.buscarHospedePorId(id);
			hospedeAtualizado.setId(HospedeSalvo.getId());
			hospedeService.salvarHospede(hospedeAtualizado);
			resposta.setResposta(hospedeAtualizado);
			logger.info(String.format("Atualizando hospede %s", id));
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
	@RequestMapping(path=Constantes.Url.URL_HOSPEDE + "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Resposta removerhospede(@PathVariable Long id) {
		Resposta resposta = new Resposta();
		try {
			hospedeService.removerHospede(id);
			logger.info(String.format("Removendo hospede %s", id));
		}catch(HospedeException e) {
			resposta.setCodigo(Constantes.Status.CODIGO_ERRO);
			resposta.setMensagem(e.getMensagem());
		}
		return resposta;
	}
	
}
