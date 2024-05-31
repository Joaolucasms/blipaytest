# Resolução

## Descrição

A solução foi criada utilizando a linguagem kotlin, em conjunto com o framework spring.

A solução é uma API REST com dois endpoints, um endpoint para fazer uma avaliação de crédito
e outro para listar todas as avaliações realizadas até o momento.

A aplicação persiste as analises de crédito em um banco de dados relacional, o H2 database,
um banco de dados que armazena os dados em memória.
O banco de dados é criado no momento em que a aplicação é iniciada e encerrado junto com a aplicação,
toda vez que a aplicação é encerrada as analises de crétido realizadas são perdidas/apagadas.

A aplicação usa o gradle como gerenciador de projeto, é necessário ter o gradle instalado
para executar a aplicação e os testes automatizados.
As instruções para instalação do gradle estão disponíveis em https://gradle.org/install/.

A aplicação conta com alguns testes automatizados para validar  as regras de negócio
envolvidas no processo de analise de crédito.
A aplicação conta com dois endpoints, mas como não existem regras de negócio associadas
ao endpoint de busca de analises de cretido não foram criados testes para esse endpoint.

## Execução

Para executar o projeto ou os testes automatizados via terminal é necessário estar na pasta
raiz do projeto.

A execução do projeto executará um servidor web na porta 8080 do host,
se essa porta não estiver disponível a aplicação não será executada

O projeto pode ser compilado com o comando:
```bash
gradle build
```

O projeto pode ser executado com o comando:
```bash
gradle bootRun
```

Os testes automatidos podem ser executados com o comando:
```bash
gradle test
```

## Endpoints

Os endpoint da aplicação podem ser requisitados utilizando os comandos a seguir.

1. Endpoint de avaliação de crédito:
```bash
curl --location 'http://localhost:8080/api/v1/credit-rating' \
--header 'Content-Type: application/json' \
--data '{
    "name": "joao",
    "age": 26,
    "monthIncome": 5000,
    "city": "campinas",
    "document": "12345678919"
}'
```
E para essa requisição será fornecida a resposta:
```json
{
    "name": "joao",
    "document": "12345678919",
    "score": 217,
    "status": "APPROVED",
    "id": "e31ddb5d-8fe1-4f24-b155-dd3b1d1d9ed2"
}
```
2. Endpoint de busca de analises de crédito:

   Os parâmetros 'page' e 'size' são opcionais.
```bash
curl --location 'http://localhost:8080/api/v1/credit-rating?page=0&size=2'
```
A resposta para essa requisição será:
```json
{
    "content": [
        {
            "name": "joao",
            "document": "12345678919",
            "score": 217,
            "status": "APPROVED",
            "id": "54c5deaf-2610-41af-9912-822e1bc33bc3"
        }
    ],
    "page": {
        "size": 2,
        "number": 0,
        "totalElements": 1,
        "totalPages": 1
    }
}
```

