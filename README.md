# 🎓 University Management System

## 📝 Description

This project is a RESTful API developed in Java with Spring Boot, aimed at managing students, professors, coordinators, courses, and subjects in a university. It implements CRUD operations for these entities and has different access levels for students, professors, and coordinators based on their roles.

## 📑 Index

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Database Configuration](#database-configuration)
- [How to Run the Project](#how-to-run-the-project)
- [Demonstration](#demonstration)
- [Folder Structure](#folder-structure)

## 🚀 Features

- **Student Registration:** Allows registering students with information such as name, email, birthdate, and course.
- **Professor Registration:** Management of professors and the subjects they teach.
- **Coordinator Registration:** Associates coordinators with courses and allows them to also teach subjects.
- **Subject Management:** Adds subjects with student limits and assigns both main and substitute professors.
- **Student Enrollment:** Enables students to enroll in courses and subjects.
- **Reports:** Generates detailed reports on students, professors, coordinators, and courses.

## 🧰 Prerequisites

Make sure you have the following tools installed:

- [Java JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [MySQL or PostgreSQL](https://dev.mysql.com/downloads/installer/): A running and configured database.
- [Maven](https://maven.apache.org/install.html): To build and manage the project dependencies.
- [Docker](https://www.docker.com/): For setting up the database using a container.


## 🛠 Configuração do Banco de Dados

1. Crie um banco de dados MySQL/PostgreSQL utilizando o Docker (ou crie manualmente):
   ```bash
   docker run --name universidade-db -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=universidade_db -p 3306:3306 -d mysql:latest
   ```

2. No arquivo `application.properties`, configure as credenciais de acesso ao banco de dados:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/universidade_db
   spring.datasource.username=root
   spring.datasource.password=admin
   ```

3. Execute o projeto para que as tabelas sejam criadas automaticamente no banco de dados.

## 🚀 Como Executar o Projeto

1. **Clone o repositório:**

    ```bash
    git clone https://github.com/VictorHugoCC/SP_SpringBoot_AWS_Desafio_02.git
    cd SP_SpringBoot_AWS_Desafio_02
    ```

2. **Compile o projeto com Maven:**

    ```bash
    mvn clean install
    ```

3. **Execute o projeto:**

    ```bash
    mvn spring-boot:run
    ```

4. **Acesse a documentação Swagger da API em:**

    ```
    http://localhost:8080/swagger-ui.html
    ```

## 📂 Estrutura de Pastas

### **Entities**

- **Aluno:** Representa os alunos matriculados nos cursos.
- **Professor:** Armazena as informações dos professores, suas disciplinas e cursos.
- **Coordenador:** Representa os coordenadores responsáveis pelos cursos.
- **Curso:** Contém as informações sobre os cursos oferecidos na universidade.
- **Disciplina:** Contém as informações sobre as disciplinas, com professores titulares e substitutos.
- **Matrícula:** Relação entre alunos e cursos.
- **User:** Serve como uma entidade de herança

### **Services**

- **AlunoService:** Responsável pela lógica de negócio do cadastro e gerenciamento de alunos.
- **ProfessorService:** Lida com o gerenciamento de professores e atribuição de disciplinas.
- **CoordenadorService:** Lida com a associação dos coordenadores aos cursos e a conversão de coordenadores para professores.
- **CursoService:** Gerencia os cursos, criação, atualização e vinculação de disciplinas.
- **DisciplinaService:** Lida com o gerenciamento de disciplinas e associação a professores e alunos.
- **MatriculaService:** Lida com a lógica de matrícula de alunos em cursos.

### **Repositories**

- **AlunoRepository:** Interface responsável pela persistência de dados de alunos.
- **ProfessorRepository:** Interface responsável pela persistência de dados de professores.
- **CoordenadorRepository:** Interface responsável pela persistência de dados de coordenadores.
- **CursoRepository:** Interface responsável pela persistência de dados de cursos.
- **DisciplinaRepository:** Interface responsável pela persistência de dados de disciplinas.
- **MatriculaRepository:** Interface responsável pela persistência de matrículas.

### **Controllers**

- **AlunoController:** Expondo os endpoints para criação e gerenciamento de alunos.
- **ProfessorController:** Controla os endpoints de professores e suas disciplinas.
- **CoordenadorController:** Controla os endpoints para coordenadores, permitindo que eles também sejam atribuídos como professores.
- **CursoController:** Expondo os endpoints de criação e gerenciamento de cursos.
- **DisciplinaController:** Controla os endpoints de gerenciamento de disciplinas e associação de professores e alunos.

### **DTOs**

- **AlunoDTO:** Definindo as representações de dados que serão expostas ou recebidas pela API em relação aos alunos.
- **ProfessorDTO:** Representações dos dados dos professores e suas disciplinas.
- **CoordenadorTeachDTO:** DTO que permite a conversão de coordenadores para professores de disciplinas.
- **CursoDTO:** Definições dos dados de curso a serem expostos ou recebidos.
- **DisciplinaDTO:** Representações de disciplinas, com seus professores titulares e substitutos.

### **Security**

- **JWTAuthenticationFilter:** Filtro responsável por gerenciar a autenticação JWT.
- **JWTAuthorizationFilter:** Filtro responsável por autorizar acessos baseados em tokens JWT.
- **SecurityConfig:** Configuração da segurança, definindo regras de acesso para diferentes perfis (aluno, professor, coordenador).


## 💡 Testes

- Implementar testes unitários com JUnit para cobrir as principais funcionalidades da API.
- Implementar testes de integração para garantir o correto funcionamento dos fluxos de negócios.

## 📄 Documentação

- **Swagger UI:** Para documentação automática da API e execução de testes diretamente na interface.
  ```
  http://localhost:8080/swagger-ui.html
  ```

---
