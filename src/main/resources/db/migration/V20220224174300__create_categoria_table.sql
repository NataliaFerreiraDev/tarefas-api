-- Criação da tabela categoria
CREATE TABLE categoria (
    id UUID PRIMARY KEY,
    nome VARCHAR(100) UNIQUE NOT NULL
);