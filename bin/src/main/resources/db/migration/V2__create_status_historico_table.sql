CREATE TABLE status_historico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    denuncia_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    data DATETIME NOT NULL,
    observacao VARCHAR(500),
    PRIMARY KEY (id),
    FOREIGN KEY (denuncia_id) REFERENCES denuncias_visa(id)
);
