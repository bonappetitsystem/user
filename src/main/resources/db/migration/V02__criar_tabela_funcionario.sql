CREATE TABLE funcionario(
     id BIGSERIAL PRIMARY KEY,
     nome VARCHAR(120) NOT NULL,
     cpf VARCHAR(11) NOT NULL UNIQUE,
     genero VARCHAR(10),
     data_nascimento DATE NOT NULL,
     matricula VARCHAR(10) NOT NULL,
     perfil VARCHAR(20) NOT NULL,
     senha VARCHAR(100) NOT NULL,
     ativo BOOLEAN NOT NULL,
     id_empresa BIGINT NOT NULL REFERENCES empresa(id) ,
     data_cadastro TIMESTAMP WITHOUT TIME ZONE,
     data_atualizacao TIMESTAMP WITHOUT TIME ZONE
)