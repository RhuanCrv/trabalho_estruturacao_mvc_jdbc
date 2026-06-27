# Cenário 1 — Sistema de Clínica Veterinária

## Tabelas Identificadas

### Tutor
| Campo    | Tipo         | Restrição       |
|----------|--------------|-----------------|
| id       | SERIAL       | PK              |
| nome     | VARCHAR(100) | NOT NULL        |
| endereco | VARCHAR(200) | NOT NULL        |
| telefone | VARCHAR(20)  | NOT NULL        |

### Animal
| Campo    | Tipo         | Restrição              |
|----------|--------------|------------------------|
| id       | SERIAL       | PK                     |
| nome     | VARCHAR(100) | NOT NULL               |
| especie  | VARCHAR(50)  | NOT NULL               |
| raca     | VARCHAR(50)  | NOT NULL               |
| id_tutor | INTEGER      | FK → tutor(id) NOT NULL|

### Consulta
| Campo   | Tipo         | Restrição                 |
|---------|--------------|---------------------------|
| id      | SERIAL       | PK                        |
| id_animal | INTEGER    | FK → animal(id) NOT NULL  |
| data    | DATE         | NOT NULL                  |
| motivo  | TEXT         | NOT NULL                  |
| valor   | NUMERIC(10,2)| NOT NULL, CHECK >= 0      |

## Script SQL (CREATE TABLE)

```sql
CREATE TABLE tutor (
    id       SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    telefone VARCHAR(20)  NOT NULL
);

CREATE TABLE animal (
    id       SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    especie  VARCHAR(50)  NOT NULL,
    raca     VARCHAR(50)  NOT NULL,
    id_tutor INTEGER      NOT NULL,
    CONSTRAINT fk_animal_tutor FOREIGN KEY (id_tutor) REFERENCES tutor(id)
);

CREATE TABLE consulta (
    id        SERIAL PRIMARY KEY,
    id_animal INTEGER        NOT NULL,
    data      DATE           NOT NULL,
    motivo    TEXT           NOT NULL,
    valor     NUMERIC(10,2)  NOT NULL,
    CONSTRAINT fk_consulta_animal FOREIGN KEY (id_animal) REFERENCES animal(id),
    CONSTRAINT chk_valor_positivo CHECK (valor >= 0)
);

Regras de Negócio
Um tutor deve ter pelo menos nome, endereço e telefone cadastrados.
Um animal só pode ser cadastrado se seu tutor já existir no sistema.
Um tutor pode ter múltiplos animais vinculados a ele.
Uma consulta só pode ser registrada se o animal já estiver cadastrado.
O valor de uma consulta não pode ser negativo (deve ser >= 0).
O sistema deve permitir consultar todas as consultas de um animal específico (histórico).
O sistema deve permitir listar todos os animais de um tutor específico.

---

### 2.2 — Mapa da Arquitetura (Cenário 1)


model/ Tutor.java ← Atributos: id, nome, endereco, telefone + getters/setters/construtores Animal.java ← Atributos: id, nome, especie, raca, idTutor + getters/setters/construtores Consulta.java ← Atributos: id, idAnimal, data (LocalDate), motivo, valor (BigDecimal)
repository/ TutorRepository.java ← save, findById, findAll, update, delete AnimalRepository.java ← save, findById, findAll, findByTutorId, update, delete ConsultaRepository.java ← save, findById, findAll, findByAnimalId, update, delete
service/ TutorService.java ← valida dados do tutor antes de salvar AnimalService.java ← verifica se tutor existe antes de salvar animal ConsultaService.java ← verifica se animal existe + valida valor >= 0 antes de salvar
controller/ TutorController.java ← recebe dados, chama TutorService, exibe resultado AnimalController.java ← recebe dados, chama AnimalService, exibe resultado ConsultaController.java ← recebe dados, chama ConsultaService, exibe resultado
util/ Conexao.java ← método estático conectar() que retorna Connection
Main.java ← orquestra o fluxo: tutor → animal → consulta
