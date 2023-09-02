
CREATE TABLE Cartorio (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    observacao VARCHAR(250),
    situacao_id VARCHAR(20) NOT NULL,
    FOREIGN KEY (situacao_id) REFERENCES Situacao(id)
);

CREATE TABLE Cartorio_Atribuicao (
	id INT AUTO_INCREMENT PRIMARY KEY,
    cartorio_id SERIAL,
    atribuicao_id VARCHAR(20),
    FOREIGN KEY (cartorio_id) REFERENCES Cartorio(id),
    FOREIGN KEY (atribuicao_id) REFERENCES Atribuicao(id)
);

INSERT INTO Cartorio (nome, observacao, situacao_id)
VALUES
    ('Cartorio 1', 'Observacao 1', 'SIT_ATIVO'),
    ('Cartorio 2', 'Observacao 2', 'SIT_ATIVO'),
    ('Cartorio 3', 'Observacao 3', 'SIT_ATIVO'),
    ('Cartorio 4', 'Observacao 4', 'SIT_BLOQUEADO'),
    ('Cartorio 5', 'Observacao 5', 'SIT_BLOQUEADO'),
    ('Cartorio 6', 'Observacao 6', 'SIT_ATIVO'),
    ('Cartorio 7', 'Observacao 7', 'SIT_ATIVO'),
    ('Cartorio 8', 'Observacao 8', 'SIT_BLOQUEADO'),
    ('Cartorio 9', 'Observacao 9', 'SIT_ATIVO'),
    ('Cartorio 10', 'Observacao 10', 'SIT_ATIVO'),
    ('Cartorio 11', 'Observacao 11', 'SIT_BLOQUEADO'),
    ('Cartorio 12', 'Observacao 12', 'SIT_ATIVO');
    
INSERT INTO Cartorio_Atribuicao (cartorio_id, atribuicao_id)
VALUES
    (1, '1a'),
    (1, '2b'),
    (2, '3c'),
    (2, '4d'),
    (3, '5e'),
    (3, '6f'),
    (4, '7g'),
    (4, '8h'),
    (5, '9i'),
    (5, '10j'),
    (6, '11k'),
    (6, '12l');