-- Adicionar coluna chamado_id na tabela manutencoes
ALTER TABLE manutencoes 
ADD COLUMN chamado_id BIGINT;

-- Comentário na coluna
COMMENT ON COLUMN manutencoes.chamado_id IS 'ID do chamado que gerou esta manutenção (opcional)';