
CREATE TABLE admin (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(200) NOT NULL,
    perfil VARCHAR(20) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_cadastro TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);