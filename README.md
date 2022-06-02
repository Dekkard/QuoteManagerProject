# Projeto de Quote Manager
Projeto de controle de Cota

## Casos de Uso

### Caso 1
Inserindo uma cota cujo Stock existe:

	{
		"stockId": "petr4",
		"quotes": {
			"2019-01-01": "10",
			"2019-01-02": "11",
			"2019-01-03": "14"
		}
	}
	
Código de resposta:

	201: CREATED
	
### Caso 2
Inserindo uma cota cujo Stock não exite:

	{
		"stockId": "petr5",
		"quotes": {
			"2019-01-01": "10",
			"2019-01-02": "11",
			"2019-01-03": "14"
		}
	}
	
Código de resposta:

	400: BAD_REQUEST

### Caso 3
Busca pelo método GET:
	
	http://localhost:8081/quote
	
Resposta:

	[
		{
			"id": "a8ea9ed9-f184-4086-b72b-1fac64f70a19",
			"stockId": "petr4",
			"quotes": {
				"2019-01-03": "14.00",
				"2019-01-02": "11.00",
				"2019-01-01": "10.00"
			}
		},
		{
			"id": "b933a3a8-850a-4245-968c-78d517fcdb44",
			"stockId": "vale5",
			"quotes": {
				"2019-01-07": "22.00",
				"2019-01-06": "21.00",
				"2019-01-05": "20.00"
			}
		}
	]

Código de resposta:

	200: OK
	
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

Código de resposta:

	200: OK
	
### Caso 5
Excluindo a memória cache:

	http://localhost:8081/stockcache
	
Resposta de resposta:
	
	202: ACCEPTED