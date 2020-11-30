# projetoRestHospedagem
Hospedagem através da API Rest:

API Rest Hospede

1. Listar itens no produto:
URL: http://localhost:8081/hospede
Método: GET

2. Adicionar produto no produto:
URL: http://localhost:8081/hospede
Método: POST
Exemplo request:
{
   "nome":"Funalo da Silva",
   "documento":"123456",
   "telefone":"9925-2211"
}

3. Remover produto do produto:
URL: http://localhost:8081/hospede/<ID_DO_PRODUTO>
Método: DELETE

4. Atualizar quantidade do produto:
URL: http://localhost:8081/hospede/<ID_DO_PRODUTO>
Método: PUT
Exemplo request:
{
	"id": 1
	"nome":"Funalo da Silva",
    "documento":"123456",
    "telefone":"9925-2211"
}

5. Listar um iten especifico no produto:
URL: http://localhost:8081/hospede/<ID_DO_PRODUTO>
Método: GET

API Rest hospedagem

1. Listar itens no hospedagem:
URL: http://localhost:8081/hospedagem
Método: GET

2. Adicionar produto no hospedagem:
URL: http://localhost:8081/hospedagem
Método: POST
Exemplo request:
{
	"hospede":{
      "id":2
	},
	"dataEntrada": "2020-11-29T03:00:00.000Z",
    "dataEntrada": "2020-11-29T03:00:00.000Z",
	"adicionarVericulo": true
}

3. Remover produto do hospedagem:
URL: http://localhost:8081/hospedagem/<ID_DO_PRODUTO>
Método: DELETE

4. Atualizar quantidade do hospedagem:
URL: http://localhost:8081/hospedagem/<ID_DO_PRODUTO>
Método: PUT
Exemplo request:
{
	"id":1,
	"hospede":{
      "id":2
	},
	"dataEntrada": "2020-11-29T03:00:00.000Z",
    "dataEntrada": "2020-11-29T03:00:00.000Z",
	"adicionarVericulo": true
}

5. Listar um iten especifico no produto:
URL: http://localhost:8081/hospedagem/<ID_DO_PRODUTO>
Método: GET

Roteiro básico de instalação
Criar o schema "desafio" na base de dados;
Definir um usuário com acesso de escrita e consulta ao novo schema;
Baixar código fonte:
git clone 
Editar o arquivo "pom.xml" atualizando as configurações da base de dados;
Executar o seguinte comando onde se encontra o arquivo pom.xml:
mvn clean install
pra rodar no angular 6:  ng serve
