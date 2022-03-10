# Projeto de Quote Manager
Projeto de controle de Cota para Processo Seletivo da Inatel

## Casos de Uso
### Caso 1
Inserindo um quote cujo Stock existe:

	{
	  "id": "c01cede4-cd45-11eb-b8bc-0242ac130003",
	  "stockId": "petr4",
	  "quotes": [
	    {
	      "data": "2019-01-01",
	      "price": "10"
	    },
	    {
	      "data": "2019-01-02",
	      "price": "11"
	    },
	    {
	      "data": "2019-01-03",
	      "price": "14"
	    }
	  ]
	}
	
Status de resposta:

	201: CREATED
	
### Caso 2
Inserindo um quote cujo Stock não exite:

	{
	    "stockId": "petro",
	    "id": "ca43",
	    "quotes": [{
	        "data": "2022-03-10",
	        "price": 31.00
	    }]
	}
	
Status de resposta:

	404: Not Found

### Caso 3
Busca pelo método get:
	
	http://localhost:8081/quote
	
Resposta:

	[
	  {
	    "stockId": "petr4",
	    "id": "c01cede4-cd45-11eb-b8bc-0242ac130003",
	    "quotes": [
	      {
	        "data": "2019-01-01T00:00:00.000+00:00",
	        "price": 10
	      },
	      {
	        "data": "2019-01-02T00:00:00.000+00:00",
	        "price": 11
	      },
	      {
	        "data": "2019-01-03T00:00:00.000+00:00",
	        "price": 14
	      }
	    ]
	  }
	]

### Caso 4
Criando memória cache para os Stocks existentes:
	
	http://localhost:8081/stockcache
	
Resposta:

	[
	  {
	    "id": "petr4",
	    "description": "Petrobras PN"
	  },
	  {
	    "id": "vale5",
	    "description": "Vale do Rio Doce PN"
	  }
	]
	
### Caso 5
Deletando a memória cache

	http://localhost:8081/stockcache
	
Resposta:
	
	200 OK