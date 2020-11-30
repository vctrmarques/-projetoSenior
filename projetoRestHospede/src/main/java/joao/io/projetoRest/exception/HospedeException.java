package joao.io.projetoRest.exception;

public class HospedeException extends Exception{

	private static final long serialVersionUID = 4638156197467247223L;
		
	private String mensagem;
	private Exception exception;
	
	public HospedeException(String mensagem, Exception exception) {
		this.mensagem = mensagem;
		if (exception == null) {
			this.exception = new Exception();
		}else {
			this.exception = exception;	
		}		
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
}
