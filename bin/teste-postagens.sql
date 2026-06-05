-- Script de teste para o sistema de postagens
-- Execute este script após inicializar o backend para testar o sistema

-- ============================================
-- TESTE 1: Verificar se as tabelas foram criadas
-- ============================================

SHOW TABLES LIKE '%postagem%';
SHOW TABLES LIKE '%anexo%';

-- ============================================
-- TESTE 2: Estrutura das tabelas
-- ============================================

DESCRIBE postagem;
DESCRIBE anexos_postagem;

-- ============================================
-- TESTE 3: Inserir dados de teste (OPCIONAL)
-- ============================================

-- Primeiro, certifique-se de que existe um usuário admin
-- Substitua ID_DO_USUARIO_ADMIN pelo ID real de um usuário com role de admin

-- Exemplo de postagem de teste:
/*
INSERT INTO postagem (
    titulo, 
    resumo, 
    conteudo, 
    categoria, 
    ativa, 
    destaque, 
    visualizacoes, 
    data_publicacao, 
    data_atualizacao, 
    autor_id
) VALUES (
    'Primeira Postagem de Teste',
    'Este é um resumo de teste para nossa primeira postagem do sistema de notícias.',
    '<h2>Conteúdo da Postagem</h2><p>Este é o conteúdo HTML da nossa primeira postagem de teste. Aqui podemos incluir <strong>texto em negrito</strong>, <em>texto em itálico</em>, e até mesmo listas:</p><ul><li>Item 1</li><li>Item 2</li><li>Item 3</li></ul><p>O sistema suporta HTML completo através do editor rich text.</p>',
    'GERAL',
    true,
    true,
    0,
    NOW(),
    NOW(),
    1  -- Substitua pelo ID real de um usuário admin
);
*/

-- ============================================
-- TESTE 4: Consultas de verificação
-- ============================================

-- Verificar se a postagem foi inserida
SELECT 
    p.id,
    p.titulo,
    p.categoria,
    p.ativa,
    p.destaque,
    p.visualizacoes,
    p.data_publicacao,
    u.nome as autor_nome,
    u.username as autor_username
FROM postagem p
LEFT JOIN usuario u ON p.autor_id = u.id
ORDER BY p.data_publicacao DESC;

-- Verificar anexos (se houver)
SELECT 
    a.id,
    a.nome,
    a.url,
    a.tipo,
    a.tamanho,
    a.data_upload,
    p.titulo as postagem_titulo
FROM anexos_postagem a
LEFT JOIN postagem p ON a.postagem_id = p.id
ORDER BY a.data_upload DESC;

-- ============================================
-- TESTE 5: Estatísticas
-- ============================================

-- Contar postagens por categoria
SELECT categoria, COUNT(*) as total
FROM postagem
GROUP BY categoria;

-- Contar postagens ativas vs inativas
SELECT 
    ativa,
    COUNT(*) as total
FROM postagem
GROUP BY ativa;

-- Postagens em destaque
SELECT COUNT(*) as postagens_destaque
FROM postagem
WHERE ativa = true AND destaque = true;

-- Total de visualizações
SELECT SUM(visualizacoes) as total_visualizacoes
FROM postagem;

-- ============================================
-- TESTE 6: URLs dos endpoints para testar no Postman/Insomnia
-- ============================================

/*
ENDPOINTS PÚBLICOS (sem autenticação):
- GET  http://localhost:8086/visa-vsa/api/postagens/publicas
- GET  http://localhost:8086/visa-vsa/api/postagens/publicas/{id}
- GET  http://localhost:8086/visa-vsa/api/postagens/destaque
- GET  http://localhost:8086/visa-vsa/api/postagens/categorias
- GET  http://localhost:8086/visa-vsa/api/postagens/health

ENDPOINTS PRIVADOS (requer autenticação):
- GET    http://localhost:8086/visa-vsa/api/postagens/admin
- GET    http://localhost:8086/visa-vsa/api/postagens/admin/{id}
- POST   http://localhost:8086/visa-vsa/api/postagens
- PUT    http://localhost:8086/visa-vsa/api/postagens/{id}
- DELETE http://localhost:8086/visa-vsa/api/postagens/{id}
- PATCH  http://localhost:8086/visa-vsa/api/postagens/{id}/status
- GET    http://localhost:8086/visa-vsa/api/postagens/autor/{autorId}
- GET    http://localhost:8086/visa-vsa/api/postagens/stats

ENDPOINTS DE ANEXOS:
- POST   http://localhost:8086/visa-vsa/api/anexos/upload/{postagemId}
- GET    http://localhost:8086/visa-vsa/api/anexos/postagem/{postagemId}
- GET    http://localhost:8086/visa-vsa/api/anexos/{anexoId}
- DELETE http://localhost:8086/visa-vsa/api/anexos/{anexoId}
- GET    http://localhost:8086/visa-vsa/api/anexos/health

EXEMPLO DE BODY PARA CRIAR POSTAGEM (POST):
{
    "titulo": "Nova Postagem via API",
    "resumo": "Resumo da postagem criada via API",
    "conteudo": "<h2>Título</h2><p>Conteúdo HTML da postagem</p>",
    "categoria": "GERAL",
    "ativa": true,
    "destaque": false
}
*/
