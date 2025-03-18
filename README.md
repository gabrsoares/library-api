# 📖 Library API

API para gerenciamento de livros, permitindo cadastrar, atualizar, buscar e excluir livros de uma biblioteca.

---

## 🚀 Tecnologias Utilizadas
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- Flyway
- Banco de Dados: PostgreSQL

---

## ✅ **Pré-requisitos**
- [Java 21+](https://www.oracle.com/java/technologies/downloads/)
- [PostgreSQL](https://www.postgresql.org/download/)
- IDE de sua preferência (IntelliJ recomendado)

---

## ⚙️ **Instalação**
1. Clone o repositório:
   ```
   git clone https://github.com/usuario/library-api.git

## 🔧️ **Configuração**
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
