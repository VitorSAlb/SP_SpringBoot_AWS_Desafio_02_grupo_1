
# 🎓 Sistema de Gerenciamento de Universidade

## 📝 Descrição

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot, que tem como objetivo gerenciar alunos, professores, coordenadores, cursos e disciplinas em uma universidade. Ele implementa operações de CRUD para essas entidades e possui diferentes níveis de acesso para alunos, professores e coordenadores, de acordo com suas funções.

## 📑 Índice

- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
- [Como Executar o Projeto](#como-executar-o-projeto)
- [Demonstração](#demonstração)
- [Estrutura de Pastas](#estrutura-de-pastas)

## 🚀 Funcionalidades

- **Cadastro de Alunos:** Permite registrar alunos com informações de nome, email, data de nascimento e curso.
- **Cadastro de Professores:** Gerenciamento de professores e disciplinas nas quais lecionam.
- **Cadastro de Coordenadores:** Associa coordenadores a cursos e permite que eles também ministrem disciplinas.
- **Gerenciamento de Disciplinas:** Adiciona disciplinas com limite de alunos e atribui professores titulares e substitutos.
- **Matrícula de Alunos:** Permite a matrícula de alunos em cursos e disciplinas.
- **Relatórios:** Geração de relatórios detalhados sobre alunos, professores, coordenadores e cursos.

## 🧰 Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- [Java JDK 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [MySQL ou PostgreSQL](https://dev.mysql.com/downloads/installer/): Um banco de dados configurado e em execução.
- [Maven](https://maven.apache.org/install.html): Para compilar e gerenciar as dependências do projeto.
- [Docker](https://www.docker.com/): Para configuração do banco de dados através de container.

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

## 🎬 Demonstração

### Exemplo de Endpoint para criação de Alunos:
```bash
POST /api/alunos
```
```json
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "johndoe@example.com",
    "birthdate": "2000-01-01",
    "course": "Sistemas de Informação"
}
```

## 💡 Testes

- Implementar testes unitários com JUnit para cobrir as principais funcionalidades da API.
- Implementar testes de integração para garantir o correto funcionamento dos fluxos de negócios.

## 📄 Documentação

- **Swagger UI:** Para documentação automática da API e execução de testes diretamente na interface.
  ```
  http://localhost:8080/swagger-ui.html
  ```

---
