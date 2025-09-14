-- DDL
CREATE TABLE endereco (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    logradouro VARCHAR(255) NOT NULL,
    numero INT NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado CHAR(2) NOT NULL,
    cep CHAR(9) NOT NULL,
    complemento VARCHAR(255)
);

-- Tabela de Filiais
CREATE TABLE filial (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    fk_endereco BIGINT NOT NULL,
    CONSTRAINT FK_filial_endereco FOREIGN KEY (fk_endereco) REFERENCES endereco(id)
);

-- Tabela de Motos
CREATE TABLE moto (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    placa VARCHAR(7) NOT NULL,
    ano INT NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    tipo_combustivel VARCHAR(50) NOT NULL,
    fk_filial BIGINT NOT NULL,
    CONSTRAINT FK_moto_filial FOREIGN KEY (fk_filial) REFERENCES filial(id)
);
-- Tabela de Funcionários
CREATE TABLE funcionario (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    fk_filial BIGINT NOT NULL,
    CONSTRAINT FK_funcionario_filial FOREIGN KEY (fk_filial) REFERENCES filial(id)
);

-- Tabela de Localizações
CREATE TABLE localizacao (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    pontox FLOAT NOT NULL,
    pontoy FLOAT NOT NULL,
    data_hora DATETIME NOT NULL,
    fonte VARCHAR(50) NOT NULL,
    fk_moto BIGINT NOT NULL,
    CONSTRAINT FK_localizacao_moto FOREIGN KEY (fk_moto) REFERENCES moto(id)
);
