# trabalho_estruturacao_mvc_jdbc

# Cenário 2 — Sistema de Oficina Mecânica

## Tabelas Identificadas

### cliente
| Campo    | Tipo         | Restrição |
|----------|--------------|-----------|
| id       | SERIAL       | PK        |
| nome     | VARCHAR(100) | NOT NULL  |
| telefone | VARCHAR(20)  | NOT NULL  |

### veiculo
| Campo      | Tipo         | Restrição                  |
|------------|--------------|----------------------------|
| id         | SERIAL       | PK                         |
| placa      | VARCHAR(10)  | NOT NULL, UNIQUE           |
| modelo     | VARCHAR(100) | NOT NULL                   |
| ano        | INTEGER      | NOT NULL                   |
| id_cliente | INTEGER      | FK → cliente(id) NOT NULL  |

### ordem_servico
| Campo     | Tipo          | Restrição                 |
|-----------|---------------|---------------------------|
| id        | SERIAL        | PK                        |
| id_veiculo| INTEGER       | FK → veiculo(id) NOT NULL |
| descricao | TEXT          | NOT NULL                  |
| valor     | NUMERIC(10,2) | NOT NULL, CHECK >= 0      |
| status    | VARCHAR(20)   | NOT NULL DEFAULT 'ABERTA' |

## Script SQL (CREATE TABLE)

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

Regras de Negócio
Um cliente precisa ter pelo menos nome e telefone cadastrados.
Um veículo só pode ser cadastrado se o cliente já existir no sistema.
Um cliente pode ter múltiplos veículos vinculados a ele.
Uma ordem de serviço só pode ser aberta para veículos já cadastrados.
O valor do serviço não pode ser negativo (deve ser >= 0).
Uma ordem de serviço tem status inicial "ABERTA" e pode ser atualizada para "CONCLUIDA".
O sistema deve permitir consultar todo o histórico de manutenções de um veículo.

---

### 3.2 — Mapa da Arquitetura (Cenário 2)


model/ Cliente.java ← id, nome, telefone Veiculo.java ← id, placa, modelo, ano, idCliente OrdemServico.java ← id, idVeiculo, descricao, valor (BigDecimal), status
repository/ ClienteRepository.java ← CRUD completo VeiculoRepository.java ← CRUD + findByClienteId OrdemServicoRepository.java ← CRUD + findByVeiculoId
service/ ClienteService.java ← valida dados VeiculoService.java ← verifica se cliente existe OrdemServicoService.java ← verifica veículo + valida valor + valida status
controller/ ClienteController.java VeiculoController.java OrdemServicoController.java

