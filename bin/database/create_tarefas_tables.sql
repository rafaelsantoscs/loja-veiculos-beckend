-- Script de criação das tabelas de Tarefas e Subtarefas
-- Sistema de Gerenciamento de Tarefas

-- Criar tabela de Tarefas
CREATE TABLE IF NOT EXISTS tarefas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    criado_por VARCHAR(255) NOT NULL,
    atribuido_para VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_prazo TIMESTAMP NULL,
    data_conclusao TIMESTAMP NULL,
    prioridade VARCHAR(50) NOT NULL DEFAULT 'MEDIA',
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE',
    observacoes TEXT,
    INDEX idx_atribuido_para (atribuido_para),
    INDEX idx_criado_por (criado_por),
    INDEX idx_status (status),
    INDEX idx_prioridade (prioridade),
    INDEX idx_data_criacao (data_criacao DESC),
    INDEX idx_data_prazo (data_prazo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Criar tabela de Subtarefas
CREATE TABLE IF NOT EXISTS subtarefas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tarefa_id BIGINT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    concluida BOOLEAN NOT NULL DEFAULT FALSE,
    data_conclusao TIMESTAMP NULL,
    concluida_por VARCHAR(255),
    ordem INT NOT NULL DEFAULT 0,
    FOREIGN KEY (tarefa_id) REFERENCES tarefas(id) ON DELETE CASCADE,
    INDEX idx_tarefa_id (tarefa_id),
    INDEX idx_ordem (ordem),
    INDEX idx_concluida (concluida)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Comentários sobre as colunas
ALTER TABLE tarefas COMMENT = 'Tabela principal de tarefas do sistema';
ALTER TABLE subtarefas COMMENT = 'Tabela de subtarefas vinculadas às tarefas principais';

-- Valores válidos para os ENUMS (documentação)
-- prioridade: BAIXA, MEDIA, ALTA, URGENTE
-- status: PENDENTE, EM_ANDAMENTO, CONCLUIDA, CANCELADA

-- Inserir dados de exemplo (opcional, apenas para testes)
-- INSERT INTO tarefas (titulo, descricao, criado_por, atribuido_para, data_prazo, prioridade, status)
-- VALUES 
--     ('Configurar Servidor', 'Configurar servidor de produção com todas as dependências', 
--      'admin@empresa.com', 'tecnico@empresa.com', DATE_ADD(NOW(), INTERVAL 7 DAY), 'ALTA', 'PENDENTE'),
--     ('Documentar API', 'Criar documentação completa da API REST', 
--      'admin@empresa.com', 'dev@empresa.com', DATE_ADD(NOW(), INTERVAL 5 DAY), 'MEDIA', 'PENDENTE');

-- INSERT INTO subtarefas (tarefa_id, titulo, ordem)
-- VALUES 
--     (1, 'Instalar sistema operacional', 0),
--     (1, 'Configurar firewall', 1),
--     (1, 'Instalar dependências', 2),
--     (1, 'Realizar testes', 3);

-- Consultas úteis para verificar o estado das tarefas:

-- Tarefas pendentes por usuário
-- SELECT t.*, 
--        (SELECT COUNT(*) FROM subtarefas WHERE tarefa_id = t.id AND concluida = TRUE) as subtarefas_concluidas,
--        (SELECT COUNT(*) FROM subtarefas WHERE tarefa_id = t.id) as total_subtarefas
-- FROM tarefas t 
-- WHERE t.atribuido_para = 'usuario@email.com' AND t.status != 'CONCLUIDA' 
-- ORDER BY t.data_prazo ASC;

-- Tarefas vencidas
-- SELECT * FROM tarefas 
-- WHERE data_prazo < NOW() AND status NOT IN ('CONCLUIDA', 'CANCELADA')
-- ORDER BY data_prazo ASC;

-- Progresso de uma tarefa específica
-- SELECT t.*, 
--        COUNT(s.id) as total_subtarefas,
--        SUM(CASE WHEN s.concluida = TRUE THEN 1 ELSE 0 END) as subtarefas_concluidas,
--        ROUND((SUM(CASE WHEN s.concluida = TRUE THEN 1 ELSE 0 END) / COUNT(s.id)) * 100, 2) as progresso_percentual
-- FROM tarefas t
-- LEFT JOIN subtarefas s ON s.tarefa_id = t.id
-- WHERE t.id = 1
-- GROUP BY t.id;
