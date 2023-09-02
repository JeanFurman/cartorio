CREATE TABLE Situacao (
    id VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

INSERT INTO Situacao (id, nome) VALUES
    ('SIT_ATIVO', 'Ativo'),
    ('SIT_BLOQUEADO', 'Bloqueado');