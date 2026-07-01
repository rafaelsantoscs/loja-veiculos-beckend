-- Script para adicionar coluna observacoes na tabela responsavel_tecnico
-- Execute este script no banco de dados MySQL

ALTER TABLE responsavel_tecnico 
ADD COLUMN observacoes TEXT DEFAULT NULL COMMENT 'Observações administrativas sobre correções necessárias';

-- Verificar se a coluna foi criada
DESCRIBE responsavel_tecnico;
