-- Script para adicionar a coluna 'lotacao' na tabela 'abastecimento_veiculo'
-- Esta coluna armazenará a secretaria onde o veículo está lotado no momento do abastecimento

ALTER TABLE abastecimento_veiculo 
ADD COLUMN lotacao VARCHAR(255) COMMENT 'Secretaria onde o veículo está lotado';

-- Índice para melhorar performance nas consultas por lotação
CREATE INDEX idx_abastecimento_veiculo_lotacao ON abastecimento_veiculo(lotacao);

-- Índice composto para consultas com múltiplos filtros
CREATE INDEX idx_abastecimento_veiculo_orgao_lotacao_data ON abastecimento_veiculo(acessado_por, lotacao, data);
CREATE INDEX idx_abastecimento_veiculo_combustivel_orgao_lotacao ON abastecimento_veiculo(combustivel, acessado_por, lotacao);
