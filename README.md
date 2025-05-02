# 📦 API de Controle de Estoque

Projeto pessoal desenvolvido com **Spring Boot** com o objetivo de criar uma API REST para gerenciamento de estoque de produtos e fornecedores.  
Além de aprofundar conhecimentos em **Spring**, o projeto aborda conceitos importantes de segurança, boas práticas em APIs.

---

## 🚀 Tecnologias e Ferramentas

- **Java 21**
- **Spring Boot 3.4**
  - Spring Web / WebFlux (testes de integração)
  - Spring Security
  - Spring Data JPA
  - Bean Validation
- **MapStruct** (v1.6.3)
- **JWT** (Json Web Token)
- **H2 Database** (para testes)
- **MySQL 8.0**
- **OpenAPI / Swagger** (documentação da API)
---

## 🔧Como Executar

### Pré-requisitos

- Java 21 instalado
- MySQL rodando localmente (ou configurado)

### Passos para rodar o projeto

1. Clone o repositório: 
```bash
git clone https://github.com/gianmarques001/api-controle-estoque.git
```
2. Rode a classe principal
```bash
EstoqueapiApplication.java
```
3. Utilize alguma ferramenta de desenvolvimento (Postman, Insomnia). API estará disponível através de: [http://localhost:8080](http://localhost:8080)



## 🧩 Endpoints de Exemplo


```bash
POST /api/v1/auth - Autenticação e geração do token JWT.

GET /api/v1/fornecedores - Listar todos os fornecedores do sistema.

GET /api/v1/estoquistas - Listar todos os estoquistas do sistema.

GET /api/v1/produtos - Listar todos os produtos no sistema.
GET /api/v1/produtos/fornecedor - Listar os produtos de um determinado fornecedor.
POST /api/v1/produtos - Salvar um produto no sistema.

POST /api/v1/solicitacoes - Salvar uma solicitação no sistema.
GET /api/v1/solicitacoes - Listar todas as solicitações no sistema.
GET /api/v1/solicitacoes/fornecedores - Listar solicitações de um determinado fornecedor.
PATCH /api/v1/solicitacoes/fornecedores/{id} - Atualizar uma solicitação de um determinado fornecedor.


```
## 💡 Aprendizados e Destaques
- Melhoria no entendimento de mapeamento de objetos com MapStruct
- Uso de Projections para otimizar consultas e retornar DTOs específicos
- Implementação de JWT com segurança no Spring Security
- Testes de integração com WebFlux, usando banco em memória


## 📬 Feedback
Este projeto foi criado com fins de aprendizado e prática.
Fique à vontade para enviar sugestões ou propor melhorias!


## 📄 Licença
Distribuído sob a licença [MIT](https://choosealicense.com/licenses/mit/).


