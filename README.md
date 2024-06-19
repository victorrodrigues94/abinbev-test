# Bebidas API

## Descrição

Projeto teste API para cadastro de bebidas, construída com Spring Boot. A API permite operações CRUD (Create, Read, Update, Delete) em um banco de dados MongoDB, e inclui funcionalidades de mensageria com RabbitMQ. A API também utiliza um banco de dados Mongo Embed para testes unitários.

## Funcionalidades

- **Cadastro de Bebidas (POST)**
  - Endpoint: `/bebidas`
  - Descrição: Cria um novo registro de bebida.
  - Payload:
    ```json
    {
      "nome": "string",
      "tipo": "string",
      "preco": "double",
      "quantidade": "integer"
    }
    ```

- **Atualização Parcial de Bebidas (PATCH)**
  - Endpoint: `/bebidas/{id}`
  - Descrição: Atualiza parcialmente um registro de bebida existente.

- **Consulta de Bebidas (GET)**
  - Endpoint: `/bebidas`
  - Descrição: Retorna todas as bebidas cadastradas. Permite filtrar por nome e tipo.

- **Mensageria**
  - Publicação de mensagens via RabbitMQ sempre que uma nova bebida for cadastrada.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.0
- MongoDB
- MongoDB Embed (Para testes)
- RabbitMQ
- Maven



## Configuração e Execução

### Pré-requisitos

- Docker
- Docker Compose

### Executando a Aplicação

1. **Clone o repositório**
   ```sh
   git clone https://github.com/victorrodrigues94/abinbev-test.git
   cd abinbev-test

2. **Execute o comando docker**
   ```sh
   docker-compose up -d --build

Ao executar o comando, o sistema realizará os testes automatizados. Caso todos os testes sejam bem-sucedidos, será gerada uma build do projeto, que será copiada para dentro do contêiner Docker na porta 8080. Três contêineres serão criados: um para a API, um para o banco de dados e um para o RabbitMQ (porta de administração 15672 - usuário e senha: guest).

Para realização dos testes manuais na API será necessário o Postman ou o Insomnia. E para viasualização das mensagens do RabbitMQ será necessário entrar no console administrativo e nos logs da API (docker logs -f bebidas-api)
