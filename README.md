# trabalho_estruturacao_mvc_jdbc

# Sistema de Oficina Mecânica (Cenário 2)

Este projeto consiste numa aplicação Java desenvolvida utilizando a arquitetura **MVC (Model-View-Controller)** e persistência de dados com **JDBC** no banco de dados **PostgreSQL**. A aplicação simula o fluxo operacional de uma oficina mecânica, gerindo clientes, veículos e ordens de serviço através de um menu interativo no terminal.

---

## 📊 1. Modelação da Base de Dados

O sistema é composto por três tabelas principais fortemente relacionadas:

### 👤 Tabela: `cliente`
| Campo | Tipo | Restrição | Descrição |
| :--- | :--- | :--- | :--- |
| **id** | SERIAL | PRIMARY KEY | Identificador único do cliente |
| **nome** | VARCHAR(100) | NOT NULL | Nome completo do cliente |
| **telefone** | VARCHAR(20) | NOT NULL | Telefone de contacto |

### 🚗 Tabela: `veiculo`
| Campo | Tipo | Restrição | Descrição |
| :--- | :--- | :--- | :--- |
| **id** | SERIAL | PRIMARY KEY | Identificador único do veículo |
| **placa** | VARCHAR(10) | NOT NULL, UNIQUE | Matrícula/Placa única do veículo |
| **modelo** | VARCHAR(100) | NOT NULL | Modelo do carro (ex: Fiat Uno) |
| **ano** | INTEGER | NOT NULL | Ano de fabrico |
| **id_cliente** | INTEGER | FK → `cliente(id)`, NOT NULL | Proprietário do veículo |

### 🛠️ Tabela: `ordem_servico`
| Campo | Tipo | Restrição | Descrição |
| :--- | :--- | :--- | :--- |
| **id** | SERIAL | PRIMARY KEY | Identificador único da OS |
| **id_veiculo** | INTEGER | FK → `veiculo(id)`, NOT NULL | Veículo associado à OS |
| **descricao** | TEXT | NOT NULL | Detalhes do serviço a ser feito |
| **valor** | NUMERIC(10,2) | NOT NULL, CHECK (valor >= 0) | Custo total do serviço |
| **status** | VARCHAR(20) | NOT NULL, DEFAULT 'ABERTA' | Estado atual (`ABERTA` ou `CONCLUIDA`) |

---

## 🛠️ 2. Script de Criação SQL (DDL)

```sql
CREATE TABLE cliente (
    id       SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)  NOT NULL
);

CREATE TABLE veiculo (
    id         SERIAL PRIMARY KEY,
    placa      VARCHAR(10)  NOT NULL UNIQUE,
    modelo     VARCHAR(100) NOT NULL,
    ano        INTEGER      NOT NULL,
    id_cliente INTEGER      NOT NULL,
    CONSTRAINT fk_veiculo_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE ordem_servico (
    id          SERIAL PRIMARY KEY,
    id_veiculo  INTEGER        NOT NULL,
    descricao   TEXT           NOT NULL,
    valor       NUMERIC(10,2)  NOT NULL,
    status      VARCHAR(20)    NOT NULL DEFAULT 'ABERTA',
    CONSTRAINT fk_os_veiculo FOREIGN KEY (id_veiculo) REFERENCES veiculo(id),
    CONSTRAINT chk_os_valor  CHECK (valor >= 0),
    CONSTRAINT chk_os_status CHECK (status IN ('ABERTA', 'CONCLUIDA'))
);

```
br.edu.faculdade/
│
├── 📦 model/            # Classes de Entidade (Representação das tabelas do banco)
│   ├── Cliente.java             <- Atributos: id, nome, telefone
│   ├── Veiculo.java             <- Atributos: id, placa, modelo, ano, idCliente
│   └── OrdemServico.java        <- Atributos: id, idVeiculo, descricao, valor, status
│
├── 📦 repository/       # Camada DAO/Repository (Consultas SQL brutas via JDBC)
│   ├── ClienteRepository.java      <- Salvar cliente e geração de ID automático
│   ├── VeiculoRepository.java      <- Salvar veículo e buscar por ID
│   └── OrdemServicoRepository.java <- CRUD Completo + Busca de histórico por veículo
│
├── 📦 service/          # Camada de Negócio (Onde as regras e validações acontecem)
│   ├── ClienteService.java         <- Valida dados obrigatórios do cliente
│   ├── VeiculoService.java         <- Verifica integridade do proprietário
│   └── OrdemServicoService.java     <- Valida regras de valores negativos e status da OS
│
├── 📦 controller/       # Orquestradores (Intermédio entre o Menu View e os Serviços)
│   ├── ClienteController.java
│   ├── VeiculoController.java
│   └── OrdemServicoController.java
│
├── 📦 util/             # Utilitários de Infraestrutura
│   └── Conexao.java                <- Criação e gestão da conexão JDBC com o PostgreSQL
│
└── 🚀 Main.java         # Ponto de entrada (Interface interativa com Scanner para o usuário)
