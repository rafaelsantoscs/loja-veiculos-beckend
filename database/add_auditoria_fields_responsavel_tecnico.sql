-- Script de migração para adicionar campos de auditoria na tabela responsavel_tecnico
-- Adiciona campos para controle de auditoria e rastreamento de ações administrativas

ALTER TABLE responsavel_tecnico 
ADD COLUMN ultima_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
ADD COLUMN atendido_por VARCHAR(255) NULL,
ADD COLUMN data_atendimento DATETIME NULL;

-- Atualizar registros existentes com a data atual
UPDATE responsavel_tecnico 
SET ultima_atualizacao = NOW() 
WHERE ultima_atualizacao IS NULL;

-- Adicionar comentários para documentação
ALTER TABLE responsavel_tecnico 
MODIFY COLUMN ultima_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP 
COMMENT 'Data e hora da última atualização do registro (criar, editar, solicitar baixa)',
MODIFY COLUMN atendido_por VARCHAR(255) NULL 
COMMENT 'Nome do usuário administrativo que atendeu a solicitação',
MODIFY COLUMN data_atendimento DATETIME NULL 
COMMENT 'Data e hora do atendimento administrativo (aprovar, corrigir ou dar baixa)';
