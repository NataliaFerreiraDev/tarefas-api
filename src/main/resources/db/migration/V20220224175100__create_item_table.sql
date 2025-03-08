-- Criação da tabela item
CREATE TABLE item (
    id UUID PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    concluido BOOLEAN NOT NULL DEFAULT FALSE,
    data_limite TIMESTAMP,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    categoria_id UUID NOT NULL,
    CONSTRAINT fk_item_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE CASCADE
);