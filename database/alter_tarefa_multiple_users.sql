-- Alterar tabela tarefas para suportar múltiplos usuários
-- O campo atribuido_para agora armazenará múltiplos emails separados por vírgula
-- Exemplo: "user1@email.com,user2@email.com,user3@email.com"

ALTER TABLE tarefas 
MODIFY COLUMN atribuido_para TEXT NOT NULL 
COMMENT 'Emails dos usuários responsáveis (separados por vírgula)';
