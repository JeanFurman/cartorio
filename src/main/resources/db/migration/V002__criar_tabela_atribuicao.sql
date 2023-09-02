CREATE TABLE Atribuicao (
    id VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    situacao BOOLEAN DEFAULT TRUE
);

INSERT INTO Atribuicao (id, nome, situacao)
VALUES
    ('1a', 'Atribuicao 1', TRUE),
    ('2b', 'Atribuicao 2', TRUE),
    ('3c', 'Atribuicao 3', TRUE),
    ('4d', 'Atribuicao 4', TRUE),
    ('5e', 'Atribuicao 5', TRUE),
    ('6f', 'Atribuicao 6', TRUE),
    ('7g', 'Atribuicao 7', TRUE),
    ('8h', 'Atribuicao 8', TRUE),
    ('9i', 'Atribuicao 9', TRUE),
    ('10j', 'Atribuicao 10', TRUE),
    ('11k', 'Atribuicao 11', TRUE),
    ('12l', 'Atribuicao 12', TRUE);