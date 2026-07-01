-- Adicionar novas colunas na tabela responsavel_tecnico
ALTER TABLE responsavel_tecnico 
ADD COLUMN cpf VARCHAR(14),
ADD COLUMN endereco VARCHAR(255),
ADD COLUMN numero VARCHAR(10),
ADD COLUMN complemento VARCHAR(100),
ADD COLUMN bairro VARCHAR(100),
ADD COLUMN cidade VARCHAR(100),
ADD COLUMN cep VARCHAR(9),
ADD COLUMN cnpj VARCHAR(18);

-- Criar índices para performance
CREATE INDEX idx_responsavel_tecnico_cpf ON responsavel_tecnico(cpf);
CREATE INDEX idx_responsavel_tecnico_cnpj ON responsavel_tecnico(cnpj);
CREATE INDEX idx_responsavel_tecnico_cidade ON responsavel_tecnico(cidade);
