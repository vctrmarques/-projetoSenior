package joao.io.projetoRest.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import joao.io.projetoRest.util.Constantes;

public class CheckIn extends Resposta{
	
	private Integer codigo;
	
	@JsonInclude(Include.NON_NULL)
	private String mensagem;
	
	public CheckIn() {
		this.codigo = Constantes.Status.CODIGO_SUCESSO;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
