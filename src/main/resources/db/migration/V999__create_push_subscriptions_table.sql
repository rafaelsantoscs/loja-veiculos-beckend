-- Criação da tabela para armazenar as subscriptions de push notifications

CREATE TABLE IF NOT EXISTS push_subscriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    endpoint VARCHAR(512) NOT NULL,
    p256dh_key VARCHAR(255) NOT NULL,
    auth_key VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    INDEX idx_username_active (username, active),
    INDEX idx_endpoint (endpoint(255))
);

-- Comentários para documentação
ALTER TABLE push_subscriptions 
COMMENT = 'Tabela para armazenar subscriptions de push notifications dos usuários';

ALTER TABLE push_subscriptions 
MODIFY COLUMN username VARCHAR(255) NOT NULL COMMENT 'Username do usuário que se inscreveu para receber notificações push',
MODIFY COLUMN endpoint VARCHAR(512) NOT NULL COMMENT 'URL do endpoint do serviço push do navegador',
MODIFY COLUMN p256dh_key VARCHAR(255) NOT NULL COMMENT 'Chave pública ECDH P-256 para criptografia',
MODIFY COLUMN auth_key VARCHAR(255) NOT NULL COMMENT 'Chave de autenticação para criptografia',
MODIFY COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Data e hora de criação da subscription',
MODIFY COLUMN active BOOLEAN DEFAULT TRUE COMMENT 'Indica se a subscription está ativa';
