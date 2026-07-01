-- Adicionar coluna para documento anexo na tabela responsavel_tecnico
ALTER TABLE responsavel_tecnico 
ADD COLUMN documento_anexo_url VARCHAR(500);

-- Comentário para documentação
COMMENT ON COLUMN responsavel_tecnico.documento_anexo_url IS 'URL do documento PDF com RG, CPF, Registro do Conselho Regional e Diploma';
