package joao.io.projetoRest.util;

public class Constantes {
	
	public interface Status{
		public static final int CODIGO_SUCESSO = 0;
		public static final int CODIGO_ERRO = -1;
	}
	
	public interface Url{
		public static final String URL_HOSPEDE = "api/hospede";
		public static final String URL_HOSPEDAGEM = "api/hospedagem";
		public static final String URL_LIMPAR_CARRINHO = "/carrinho/limpar";
	}
}
