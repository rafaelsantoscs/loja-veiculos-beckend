-- Adicionar coluna tipo_estabelecimento na tabela dados_do_estabelecimento
ALTER TABLE dados_do_estabelecimento 
ADD COLUMN tipo_estabelecimento VARCHAR(255);

-- Comentário da coluna
COMMENT ON COLUMN dados_do_estabelecimento.tipo_estabelecimento IS 'Tipo de estabelecimento selecionado no formulário';