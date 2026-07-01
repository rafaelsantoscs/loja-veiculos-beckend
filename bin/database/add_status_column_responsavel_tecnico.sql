-- Script para adicionar a coluna 'status' na tabela responsavel_tecnico
-- Execute este script no seu banco de dados

-- Adicionar a coluna status
ALTER TABLE responsavel_tecnico 
ADD COLUMN status VARCHAR(50) DEFAULT 'Dados Em Análise';

-- Atualizar registros existentes para definir status padrão
UPDATE responsavel_tecnico 
SET status = 'Dados Em Análise' 
WHERE status IS NULL;

-- Comentário explicativo
-- Valores possíveis para status:
-- 'Dados Em Análise' - Status inicial para novos registros ou registros em análise
-- 'Ativo' - Responsável técnico aprovado e ativo pelo setor administrativo
-- 'Baixa Solicitada' - Usuário solicitou baixa, aguardando confirmação do setor administrativo
-- 'Baixa Confirmada' - Baixa de responsabilidade técnica confirmada pelo setor administrativo
