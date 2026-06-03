-- Script para adicionar campo documento_anexo_url na tabela responsavel_tecnico
-- Executar no banco de dados MySQL

-- Verificar se a coluna já existe antes de adicionar
SET @sql = (
    SELECT CASE 
        WHEN COUNT(*) = 0 THEN 
            'ALTER TABLE responsavel_tecnico ADD COLUMN documento_anexo_url VARCHAR(500) NULL COMMENT ''URL do documento anexado (RG, CPF, Registro do Conselho, Diploma)'''
        ELSE 
            'SELECT ''Coluna documento_anexo_url já existe'' AS info'
    END
    FROM information_schema.columns 
    WHERE table_schema = DATABASE() 
        AND table_name = 'responsavel_tecnico' 
        AND column_name = 'documento_anexo_url'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Verificar se a coluna foi adicionada com sucesso
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_COMMENT
FROM information_schema.columns 
WHERE table_schema = DATABASE() 
    AND table_name = 'responsavel_tecnico' 
    AND column_name = 'documento_anexo_url';
