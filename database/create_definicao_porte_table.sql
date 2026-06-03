-- Criação da tabela definicao_porte
CREATE TABLE definicao_porte (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cnpj_cpf_empresa VARCHAR(18) NOT NULL,
    empresa_id BIGINT,
    quantidade_gerada DOUBLE NOT NULL,
    porte VARCHAR(20) NOT NULL,
    responsavel_tipo VARCHAR(10) NOT NULL,
    responsavel_id BIGINT NOT NULL,
    responsavel_nome VARCHAR(255),
    registrado_por VARCHAR(255),
    data_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    cpf_do_registrador VARCHAR(14),
    usuario_id BIGINT,
    
    INDEX idx_cnpj_cpf_empresa (cnpj_cpf_empresa),
    INDEX idx_empresa_id (empresa_id),
    INDEX idx_cpf_registrador (cpf_do_registrador),
    INDEX idx_usuario_id (usuario_id),
    INDEX idx_data_registro (data_registro)
);
