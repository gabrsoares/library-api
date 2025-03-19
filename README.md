# Library API

API para gerenciamento de livros, permitindo cadastrar, atualizar, buscar e excluir livros de uma biblioteca.

---

## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- Flyway
- Banco de Dados: PostgreSQL

---

## **Pré-requisitos**
- [Java 21+](https://www.oracle.com/java/technologies/downloads/)
- [PostgreSQL](https://www.postgresql.org/download/)
- IDE de sua preferência (IntelliJ recomendado)

---

## **Instalação**
1. Clone o repositório:
   ```
   git clone https://github.com/usuario/library-api.git

## **Configuração**
1. Definição do usuário no banco de dados

    Opção 1: Atualize os dados de login diretamente no arquivo de configuração abaixo:
    ```
    src/main/resources/application.properties
    ```
    Opção 2: Atualizar as variáveis de ambiente na sua IDE
    ```properties
    DB_USER=usuario
    DB_PASS=senha
   
2. Criar o banco de dados chamado ``library``

3. Executar a API pela classe LibraryApiApplication
    ```
    src/main/java/com/library_api/LibraryApiApplication.java

## Roles
- **User:** Pode acessar a busca de livros, sem possibilidade de removê-los ou alterá-los.
- **Admin:** Pode acessar os livros, alterá-los, removê-los e excluir usuários, por questões de segurança,
  um administrador não pode se excluir, é necessário realizar a exclusão através de outro administrador.


##  Endpoints

## Usuários

### Registro
**POST** `http://localhost:8080/auth/register`

- **Descrição:** Registra o usuário no banco de dados, é necessário escolher uma role para realizar o cadastro.
```
{
    "login": "Insira seu nome",
    "password": "Insira sua senha",
    "role": "ADMIN" ou "USER"
}
```
### **Login**

**POST** `http://localhost:8080/auth/login`
- **Descrição:** Autentica o usuário e retorna um token JWT.
  ```
  {
    "username": "user",
    "password": "password"
  }
  ```
- Para utilizar o token enviado na resposta, deve-se escolher a opção de Bearer Token

### **Remover usuário**

**DELETE** `http://localhost:8080/auth/delete`
```
{
    "login": "Teste1234"
}
```

## Livros
### **IMPORTANTE:**
- Todos as requisições devem utilizar tokens.
- Os tokens expiram em duas horas.

### **Busca de livros**
**GET** `http://localhost:8080/books`

- Retorna lista com todos os livros com atributos ID, título, autor e ano.
```
[
	{
		"id": 1,
		"title": "Livro 1",
		"author": "Autor 1",
		"year": 1990
	},
	{
		"id": 2,
		"title": "Livro 2",
		"author": "Autor 2",
		"year": 2001
	},
]
```

- Também é possível realizar a busca através do ID

**GET** `http://localhost:8080/books/{id}`

- Retorna um único livro que corresponde ao ID inserido.

### **Inserção de livros**

- Insere o livro no banco de dados com os dados enviados no corpo da requisição.

**POST** `http://localhost:8080/books/`
```
{
	"title":"Titulo do livro",
	"author":"Nome do autor",
	"year": Ano publicado
}
```

### **Atualização de livros**
**PUT** `http://localhost:8080/books/`
- Atualiza o livro com os dados inseridos no corpo da requisição.
```
{
    "id": id do livro existente
    "title":"Titulo do livro",
    "author":"Nome do autor",
    "year": Ano publicado
}
```

### **Remoção de livros**
- Exclui o livro de acordo com o ID inserido na requisição.

**DELETE** `http://localhost:8080/books/{id}`


## Tratamentos de erro

### Os erros serão exibidos em JSON no seguinte formato:
```
{
"error": "Mensagem de erro",
"status": HTTP Status (UNAUTHORIZED, NOT_FOUND, FORBIDDEN, ETC.)
}
```

### Erros de sintaxe ou por má formação de JSON não serão exibidos.

---

## Licença

Este projeto está licenciado sob a Licença MIT.  
Você pode fazer uso, cópia, modificação, fusão, publicação, distribuição, sublicenciamento e/ou venda de cópias do software, desde que mantenha os direitos autorais originais.

Para mais detalhes, consulte o arquivo [LICENSE](./LICENSE).
## Contribuição

Contribuições são sempre bem-vindas!  
Sinta-se à vontade para abrir uma _issue_ para relatar problemas ou sugerir melhorias.  
Para contribuir diretamente, faça um _fork_ do projeto, crie uma _branch_ e envie um _pull request_.


## Agradecimentos

Um agradecimento especial a [Fernanda Kipper](https://www.youtube.com/@kipperdev) pelas aulas incríveis que foram fundamentais para a criação desta API.  