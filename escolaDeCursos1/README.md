# trabalho_estruturacao_mvc_jdbc

## Sistema de Escola de Cursos Livres (Cenário 1)

Este projeto consiste numa aplicação Java desenvolvida utilizando a arquitetura MVC (Model-View-Controller) e persistência de dados com JDBC no banco de dados PostgreSQL. A aplicação simula o fluxo operacional de uma instituição de ensino, gerindo alunos, cursos e matrículas através de um menu interativo no terminal.

### 1. Modelação da Base de Dados

O sistema é composto por três tabelas principais fortemente relacionadas:

### Tabela: aluno
| Campo | Tipo | Restrição | Descrição |
| :--- | :--- | :--- | :--- |
| **id** | SERIAL | PRIMARY KEY | Identificador único do aluno |
| **nome** | VARCHAR(100) | NOT NULL | Nome completo do aluno |
| **email** | VARCHAR(150) | NOT NULL, UNIQUE | E-mail de contato único do aluno |
| **telefone** | VARCHAR(20) | NOT NULL | Telefone de contato |

### Tabela: curso
| Campo | Tipo | Restrição | Descrição |
| :--- | :--- | :--- | :--- |
| **id** | SERIAL | PRIMARY KEY | Identificador único do curso |
| **nome** | VARCHAR(100) | NOT NULL | Nome do curso (ex: Java para Iniciantes) |
| **descricao** | TEXT | NOT NULL | Detalhes e ementa do curso |
| **carga_horaria** | INTEGER | NOT NULL | Carga horária total em horas |
| **vagas_totais** | INTEGER | NOT NULL | Quantidade de vagas abertas inicialmente |
| **vagas_disponiveis** | INTEGER | NOT NULL | Vagas restantes (decrementadas a cada matrícula) |

### Tabela: matricula
| Campo | Tipo | Restrição | Descrição |
| :--- | :--- | :--- | :--- |
| **id** | SERIAL | PRIMARY KEY | Identificador único da matrícula |
| **id_aluno** | INTEGER | FK → aluno(id), NOT NULL | Aluno associado à matrícula |
| **id_curso** | INTEGER | FK → curso(id), NOT NULL | Curso associado à matrícula |
| **data_matricula** | DATE | NOT NULL | Data em que a matrícula foi realizada |
| **valor** | NUMERIC(10,2) | NOT NULL, CHECK (valor >= 0) | Valor pago pelo curso |

---

###️ 2. Script de Criação SQL (DDL)

```sql
CREATE TABLE aluno (
    id       SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    email    VARCHAR(150) NOT NULL UNIQUE,
    telefone VARCHAR(20)  NOT NULL
);

CREATE TABLE curso (
    id                SERIAL PRIMARY KEY,
    nome              VARCHAR(100) NOT NULL,
    descricao         TEXT         NOT NULL,
    carga_horaria     INTEGER      NOT NULL,
    vagas_totais      INTEGER      NOT NULL,
    vagas_disponiveis INTEGER      NOT NULL,
    CONSTRAINT chk_vagas CHECK (vagas_disponiveis >= 0 AND vagas_disponiveis <= vagas_totais)
);

CREATE TABLE matricula (
    id              SERIAL PRIMARY KEY,
    id_aluno        INTEGER        NOT NULL,
    id_curso        INTEGER        NOT NULL,
    data_matricula  DATE           NOT NULL,
    valor           NUMERIC(10,2)  NOT NULL,
    CONSTRAINT fk_matricula_aluno FOREIGN KEY (id_aluno) REFERENCES aluno(id),
    CONSTRAINT fk_matricula_curso FOREIGN KEY (id_curso) REFERENCES curso(id),
    CONSTRAINT chk_valor_matricula CHECK (valor >= 0),
    CONSTRAINT uq_aluno_curso UNIQUE (id_aluno, id_curso)
);