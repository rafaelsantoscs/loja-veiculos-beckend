-- Script SQL para criar a tabela responsavel_legal
-- Execute este script no seu banco de dados

CREATE TABLE responsavel_legal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    cnpj_cpf_empresa VARCHAR(18),
    telefone1 VARCHAR(20),
    telefone2 VARCHAR(20),
    email VARCHAR(255),
    registrado_por VARCHAR(255),
    data_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    cpf_do_registrador VARCHAR(14),
    usuario_id BIGINT,
    empresa_id BIGINT,
    
    -- Índices para melhorar performance
    INDEX idx_cpf_registrador (cpf_do_registrador),
    INDEX idx_usuario_id (usuario_id),
    INDEX idx_empresa_id (empresa_id),
    INDEX idx_cnpj_cpf_empresa (cnpj_cpf_empresa),
    INDEX idx_cpf (cpf)
);

-- Comentários para documentação
ALTER TABLE responsavel_legal 
COMMENT = 'Tabela para armazenar dados dos responsáveis legais das empresas';

ALTER TABLE responsavel_legal 
MODIFY COLUMN nome VARCHAR(255) NOT NULL COMMENT 'Nome completo do responsável legal',
MODIFY COLUMN cpf VARCHAR(14) NOT NULL COMMENT 'CPF do responsável legal (formato: 000.000.000-00)',
MODIFY COLUMN cnpj_cpf_empresa VARCHAR(18) COMMENT 'CNPJ/CPF da empresa vinculada',
MODIFY COLUMN telefone1 VARCHAR(20) COMMENT 'Telefone principal de contato',
MODIFY COLUMN telefone2 VARCHAR(20) COMMENT 'Telefone secundário de contato',
MODIFY COLUMN email VARCHAR(255) COMMENT 'E-mail do responsável legal',
MODIFY COLUMN registrado_por VARCHAR(255) COMMENT 'Nome do usuário que registrou',
MODIFY COLUMN data_registro DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Data e hora do registro',
MODIFY COLUMN cpf_do_registrador VARCHAR(14) COMMENT 'CPF do usuário que fez o registro',
MODIFY COLUMN usuario_id BIGINT COMMENT 'ID do usuário que fez o registro',
MODIFY COLUMN empresa_id BIGINT COMMENT 'ID da empresa vinculada (FK para dados_do_estabelecimento)';
