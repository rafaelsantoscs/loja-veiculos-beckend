-- Adicionar colunas area_atuacao e outro_area na tabela dados_do_estabelecimento
ALTER TABLE dados_do_estabelecimento 
ADD COLUMN area_atuacao VARCHAR(50),
ADD COLUMN outro_area VARCHAR(255);

-- Comentários das colunas
COMMENT ON COLUMN dados_do_estabelecimento.area_atuacao IS 'Área de atuação da VISA: alimentos, servico_saude, outros_servicos, produtos, medicamentos, outro_area';
COMMENT ON COLUMN dados_do_estabelecimento.outro_area IS 'Especificação da área quando selecionado outro_area';
