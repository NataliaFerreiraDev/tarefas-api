# **Tarefas API**

API para gerenciamento de tarefas, permitindo a criação, edição e remoção de categorias e tarefas.  
Esta API faz parte de um sistema completo de gestão de tarefas, cujo front-end pode ser encontrado no seguinte repositório:

🔗 Front-end: [tarefas-front](https://github.com/NataliaFerreiraDev/tarefas-front)

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway (para migração de banco de dados)**
- **Docker** (para configuração e execução do banco de dados)
- **JUnit 5 e Mockito** (para testes unitários)
- **Swagger (para a documentação da API)**
- **Arquitetura Hexagonal** (para a organização e separação de responsabilidades)

## Como Rodar o Projeto

### Pré-requisitos

Antes de rodar o projeto, você precisa ter o **Docker** e o **Docker Compose** instalados no seu ambiente local. Não é necessário instalar Java, Maven ou PostgreSQL, pois esses serviços serão gerenciados automaticamente pelos containers Docker.

- **Docker**: Para criar e rodar os containers da aplicação e do banco de dados.
- **Docker Compose**: Para orquestrar a criação e execução dos containers.

### 1. Clonar o Repositório

Primeiro, clone o repositório do projeto para sua máquina local:

```bash
git clone https://github.com/NataliaFerreiraDev/tarefas-api.git
```

### 2. Construir a Aplicação

Com o repositório clonado, o próximo passo é construir a aplicação. Se você optar por usar **Docker**, esse passo será tratado automaticamente pelo `docker-compose`. Caso contrário, você pode usar o **Maven** para gerar o arquivo JAR da aplicação.

#### Com Docker:

Para construir a aplicação utilizando o **Docker**, execute o comando abaixo:

```bash
docker-compose build
```

Esse comando irá criar a imagem Docker para a aplicação com base no arquivo `Dockerfile` presente no repositório.

### 3. Iniciar o Docker com o Banco de Dados e a Aplicação

Depois de construir a aplicação, o próximo passo é inicializar os containers. 
Esse comando criará e iniciará os containers para a aplicação e o banco de dados PostgreSQL conforme configurado no arquivo `docker-compose.yml`.

Execute o seguinte comando:

```bash
docker-compose up -d
```

- **Container da aplicação**: Será iniciado na porta `8081`.
- **Container do PostgreSQL**: Será configurado e iniciado automaticamente.

Após a execução do comando, a aplicação estará disponível em `http://localhost:8081`.

### 4. Acessar o Swagger para Documentação Interativa

A aplicação vem com o **Swagger UI**, que fornece uma documentação interativa para a API. 
Acesse o Swagger UI no seguinte endereço para explorar as rotas e testar os endpoints da aplicação:
```bash
http://localhost:8081/swagger-ui.html
```

No **Swagger**, você poderá:

- Consultar as rotas disponíveis.
- Ver exemplos de requisição e resposta.
- Interagir diretamente com a API para testar os endpoints.


### 5. Monitoramento e Métricas com Spring Actuator

Este projeto utiliza o **Spring Actuator** para fornecer métricas e informações sobre o estado de saúde da aplicação.
O **Spring Actuator** é uma excelente ferramenta para monitoramento e observabilidade, facilitando a visualização
do comportamento da aplicação em produção.
Neste projeto, o seguinte endpoint está disponível:
- **/actuator/health**: Exibe o estado de saúde da aplicação (UP, DOWN, etc.). Este endpoint é útil para monitorar se a aplicação está funcionando corretamente.

```bash
http://localhost:8081/actuator/health
```

## Endpoints - Categorias

### POST /categorias
Cria uma nova categoria de tarefas.

#### Parâmetros de Entrada:
- `nome` (string, obrigatório): Nome da categoria.

### Exemplo de Requisição:
```markdown
POST http://localhost:8081/categorias
```

**Corpo da Requisição:**

```json
{
  "nome": "Trabalho"
}
```

#### Parâmetros de Saída:
- `id` (UUID): Identificador único da categoria.
- `nome` (string): Nome da categoria.

#### Exemplo de Resposta:
```json
{
  "id": "f322461b-e295-46f8-85a6-0ce6aae27916",
  "nome": "Consultas"
}
```

### GET /categorias
Lista todas as categorias cadastradas.

#### Parâmetros de Entrada:
- Nenhum.

### Exemplo de Requisição:

```markdown
GET http://localhost:8081/categorias
```

#### Parâmetros de Saída:
- `categorias` (array de objetos): Lista com todas as categorias cadastradas.
    - Cada objeto contém:
        - `id` (UUID): Identificador único da categoria.
        - `nome` (string): Nome da categoria.

#### Exemplo de Resposta:
```json
[
  {
    "id": "0e28c350-49c7-4d20-992b-513e712594e9",
    "nome": "Exame"
  },
  {
    "id": "f322461b-e295-46f8-85a6-0ce6aae27916",
    "nome": "Consultas"
  }
]
```

### GET /categorias/{id}
Consulta uma categoria específica pelo seu ID.

#### Parâmetros de Entrada:
- `id` (UUID, obrigatório): ID único da categoria.

### Exemplo de Requisição:

```markdown
GET http://localhost:8081/categorias/f322461b-e295-46f8-85a6-0ce6aae27916
```

#### Parâmetros de Saída:
- `id` (UUID): Identificador único da categoria.
- `nome` (string): Nome da categoria.

#### Exemplo de Resposta:
```json
{
  "id": "f322461b-e295-46f8-85a6-0ce6aae27916",
  "nome": "Consultas"
}
```

### PUT /categorias/{id}
Atualiza uma categoria existente pelo ID.

#### Parâmetros de Entrada:
- `id` (UUID, obrigatório): ID único da categoria.
- `nome` (string, obrigatório): Novo nome da categoria.

### Exemplo de Requisição:

```markdown
PUT http://localhost:8081/categorias/747ab89b-0e9e-4c21-aeda-36daec191de7
```
**Corpo da Requisição:**

```json
{
  "nome": "Estudos"
}
```

#### Parâmetros de Saída:
- `id` (UUID): Identificador único da categoria.
- `nome` (string): Nome atualizado da categoria.

#### Exemplo de Resposta:
```json
{
  "id": "747ab89b-0e9e-4c21-aeda-36daec191de7",
  "nome": "Estudos"
}
```

### DELETE /categorias/{id}
Exclui uma categoria existente pelo ID.

#### Parâmetros de Entrada:
- `id` (UUID, obrigatório): ID único da categoria.

### Exemplo de Requisição:

```markdown
DELETE http://localhost:8081/categorias/747ab89b-0e9e-4c21-aeda-36daec191de7
```

#### Parâmetros de Saída:
- `Status Code`: 204 No Content.

## Endpoints - Itens

### POST /itens
Cria um novo item.

#### Parâmetros de Entrada:
- `descricao` (string, obrigatório): Descrição do item.
- `dataLimite` (string, obrigatório): Data limite para conclusão do item (formato: YYYY-MM-DD).
- `categoriaId` (UUID, obrigatório): ID da categoria à qual o item pertence.

### Exemplo de Requisição:
```markdown
POST http://localhost:8081/itens
```

**Corpo da Requisição:**

```json
{
  "descricao": "Estudar Spring",
  "dataLimite": "2025-03-30",
  "categoriaId": "747ab89b-0e9e-4c21-aeda-36daec191de7"
}
```

#### Parâmetros de Saída:
- `id` (UUID): Identificador único do item.
- `descricao` (string): Descrição do item.
- `concluido` (boolean): Status de conclusão do item.
- `dataCriacao` (string): Data de criação do item.
- `dataLimite` (string): Data limite para conclusão do item.
- `categoriaId` (UUID): ID da categoria associada ao item.

#### Exemplo de Resposta:
```json
{
  "id": "b1c7009c-7487-4ba3-9537-1ee45a7c7a68",
  "descricao": "Estudar Spring",
  "concluido": false,
  "dataCriacao": "10/03/2025 09:51",
  "dataLimite": "30/03/2025 22:00",
  "categoriaId": "747ab89b-0e9e-4c21-aeda-36daec191de7"
}
```

### GET /itens
Lista todos os itens de uma categoria.

#### Parâmetros de Entrada:
- `idCategoria` (UUID): Identificador único da categoria.

### Exemplo de Requisição:

```markdown
GET http://localhost:8081/itens?idCategoria=747ab89b-0e9e-4c21-aeda-36daec191de7
```

#### Parâmetros de Saída:
- `itens ` (array de objetos): Lista com todos os itens cadastrados.
    - Cada objeto contém:
        - `id` (UUID): Identificador único do item.
        - `descricao` (string): Descrição do item.
        - `concluido` (boolean): Status de conclusão do item.
        - `dataCriacao` (string): Data de criação do item.
        - `dataLimite` (string): Data limite para conclusão do item.
        - `categoriaId` (UUID): ID da categoria associada ao item.

#### Exemplo de Resposta:
```json
[
  {
    "id": "217af82b-4819-4824-b947-639140460108",
    "descricao": "Estudar Spring",
    "concluido": false,
    "dataCriacao": "10/03/2025 09:51",
    "dataLimite": "30/03/2025 22:00",
    "categoriaId": "747ab89b-0e9e-4c21-aeda-36daec191de7"
  },
  {
    "id": "b1c7009c-7487-4ba3-9537-1ee45a7c7a68",
    "descricao": "Estudar Docker",
    "concluido": false,
    "dataCriacao": "11/03/2025 08:40",
    "dataLimite": "30/03/2025 22:00",
    "categoriaId": "747ab89b-0e9e-4c21-aeda-36daec191de7"
  }
]
```

### GET /itens/{id}
Consulta um item específico pelo seu ID.

#### Parâmetros de Entrada:
- `id` (UUID, obrigatório): ID único do item.

### Exemplo de Requisição:

```markdown
GET http://localhost:8081/itens/747ab89b-0e9e-4c21-aeda-36daec191de7
```

#### Parâmetros de Saída:
- `id` (UUID): Identificador único do item.
- `descricao` (string): Descrição do item.
- `concluido` (boolean): Status de conclusão do item.
- `dataCriacao` (string): Data de criação do item.
- `dataLimite` (string): Data limite para conclusão do item.
- `categoriaId` (UUID): ID da categoria associada ao item.

#### Exemplo de Resposta:
```json
{
  "id": "b1c7009c-7487-4ba3-9537-1ee45a7c7a68",
  "descricao": "Estudar Spring",
  "concluido": false,
  "dataCriacao": "10/03/2025 09:51",
  "dataLimite": "30/03/2025 22:00",
  "categoriaId": "747ab89b-0e9e-4c21-aeda-36daec191de7"
}
```

### PUT /itens/{id}
Atualiza um item existente pelo ID.

#### Parâmetros de Entrada:
- `id` (UUID, obrigatório): ID único do item.
- `descricao` (string, obrigatório): Descrição do item.
- `concluido` (boolean, obrigatório): Indica se o item foi concluído.
- `dataLimite` (string, obrigatório): Data limite para conclusão do item (formato: YYYY-MM-DD).
- `categoriaId` (UUID, obrigatório): ID da categoria à qual o item pertence.

### Exemplo de Requisição:

```markdown
PUT http://localhost:8081/itens/747ab89b-0e9e-4c21-aeda-36daec191de7
```
**Corpo da Requisição:**

```json
{
  "descricao": "Estudar Spring",
  "concluido": true,
  "dataCriacao": "10/03/2025 09:51",
  "dataLimite": "30/03/2025 22:00"
}
```

#### Parâmetros de Saída:
- `id` (UUID): Identificador único do item.
- `descricao` (string): Descrição do item.
- `concluido` (boolean): Status de conclusão do item.
- `dataAtualizacao` (string): Data de atuaização do item.
- `dataLimite` (string): Data limite para conclusão do item.
- `categoriaId` (UUID): ID da categoria associada ao item.

#### Exemplo de Resposta:
```json
{
  "id": "217af82b-4819-4824-b947-639140460108",
  "descricao": "Estudar Spring",
  "concluido": true,
  "dataAtualizacao": "10/03/2025 09:58",
  "dataLimite": "15/03/2025 22:00",
  "categoriaId": "0ec97cbd-d730-475a-bbe9-76ef76edf952"
}
```

### DELETE /itens/{id}
Exclui um item existente pelo ID.

#### Parâmetros de Entrada:
- `id` (UUID, obrigatório): ID único do item.

### Exemplo de Requisição:

```markdown
DELETE http://localhost:8081/itens/747ab89b-0e9e-4c21-aeda-36daec191de7
```

#### Parâmetros de Saída:
- `Status Code`: 204 No Content.


## Arquitetura

O projeto segue o padrão **Arquitetura Hexagonal**, também conhecido como **Arquitetura Limpa**. 
Esse padrão busca separar as responsabilidades da aplicação de forma que o núcleo da lógica de negócios não dependa de 
frameworks, bancos de dados ou outras infraestruturas.

### Componentes da Arquitetura:
1. **Camada de Domínio**:
    - Contém as regras de negócio principais, como as lógicas para a execução de um jogo e as validações de entrada.
    - Não depende de nada além de interfaces e entidades simples.

2. **Camada de Aplicação**:
    - Responsável pela comunicação entre a camada de domínio e os adaptadores externos (como banco de dados, serviços externos, etc.).
    - Utiliza interfaces para interagir com a camada de domínio.

3. **Adaptadores (Camada Externa)**:
    - Adaptadores que conectam a aplicação ao mundo externo, como o banco de dados, a API (controladores REST), etc.

### Como a Arquitetura Hexagonal Se Aplica ao Código:
A comunicação entre a camada de domínio e os serviços externos é feita por meio de **interfaces**. Por exemplo:
- O controlador REST chama a camada de serviço, que por sua vez, interage com a camada de domínio.
- O banco de dados é acessado através de **repositories** que são implementados por adaptadores (ex: JDBC, JPA).

Isso garante que a lógica de negócios possa ser testada e desenvolvida de forma independente de qualquer framework ou banco de dados.

**Desenvolvido por**: Natalia Ferreira D'Angelo
