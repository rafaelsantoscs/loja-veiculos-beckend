-- Script para criar a tabela responsavel_tecnico
-- Data: 09/09/2025

USE `usuariossenhas`;

CREATE TABLE IF NOT EXISTS `responsavel_tecnico` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL COMMENT 'Nome do responsável técnico',
  `telefone` varchar(20) DEFAULT NULL COMMENT 'Telefone de contato',
  `conselho_profissional` varchar(100) DEFAULT NULL COMMENT 'Conselho profissional (CREA, CRF, etc.)',
  `registro_conselho` varchar(50) DEFAULT NULL COMMENT 'Número de registro no conselho',
  `email` varchar(255) DEFAULT NULL COMMENT 'E-mail do responsável técnico',
  `cnpj_cpf_empresa` varchar(18) DEFAULT NULL COMMENT 'CNPJ/CPF da empresa vinculada',
  `registrado_por` varchar(255) DEFAULT NULL COMMENT 'Nome do usuário que registrou',
  `data_registro` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Data e hora do registro',
  `cpf_do_registrador` varchar(14) DEFAULT NULL COMMENT 'CPF do usuário que registrou',
  `usuario_id` bigint DEFAULT NULL COMMENT 'ID do usuário que criou o registro',
  `empresa_id` bigint DEFAULT NULL COMMENT 'ID da empresa vinculada',
  PRIMARY KEY (`id`),
  KEY `idx_responsavel_tecnico_cpf_registrador` (`cpf_do_registrador`),
  KEY `idx_responsavel_tecnico_usuario_id` (`usuario_id`),
  KEY `idx_responsavel_tecnico_empresa_id` (`empresa_id`),
  KEY `idx_responsavel_tecnico_cnpj_cpf_empresa` (`cnpj_cpf_empresa`),
  KEY `idx_responsavel_tecnico_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Tabela para armazenar dados dos responsáveis técnicos';

-- Inserir comentários nas colunas
ALTER TABLE `responsavel_tecnico` 
MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Chave primária única do responsável técnico',
MODIFY COLUMN `nome` varchar(255) DEFAULT NULL COMMENT 'Nome completo do responsável técnico',
MODIFY COLUMN `telefone` varchar(20) DEFAULT NULL COMMENT 'Telefone de contato do responsável técnico',
MODIFY COLUMN `conselho_profissional` varchar(100) DEFAULT NULL COMMENT 'Nome do conselho profissional (CREA, CRF, CRM, etc.)',
MODIFY COLUMN `registro_conselho` varchar(50) DEFAULT NULL COMMENT 'Número de registro no conselho profissional',
MODIFY COLUMN `email` varchar(255) DEFAULT NULL COMMENT 'Endereço de e-mail do responsável técnico',
MODIFY COLUMN `cnpj_cpf_empresa` varchar(18) DEFAULT NULL COMMENT 'CNPJ ou CPF da empresa à qual o responsável está vinculado',
MODIFY COLUMN `registrado_por` varchar(255) DEFAULT NULL COMMENT 'Nome do usuário que realizou o cadastro',
MODIFY COLUMN `data_registro` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Data e hora em que o registro foi criado',
MODIFY COLUMN `cpf_do_registrador` varchar(14) DEFAULT NULL COMMENT 'CPF do usuário que realizou o cadastro',
MODIFY COLUMN `usuario_id` bigint DEFAULT NULL COMMENT 'ID do usuário proprietário do registro',
MODIFY COLUMN `empresa_id` bigint DEFAULT NULL COMMENT 'ID da empresa no sistema';

-- Verificar se a tabela foi criada corretamente
SELECT 'Tabela responsavel_tecnico criada com sucesso!' as status;
