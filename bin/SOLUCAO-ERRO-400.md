# SOLUÇÃO PARA ERRO 400 - Carregar Tarefas

## 🔴 Problema Identificado

O erro 400 (Bad Request) está ocorrendo porque:
- ✅ O **frontend** já foi atualizado para usar `@RequestParam` (query parameters)
- ✅ O **código fonte do backend** já foi atualizado para usar `@RequestParam`
- ❌ O **backend compilado em execução** ainda está com o código antigo (`@PathVariable`)

**URL que está sendo chamada:**
```
GET http://localhost:8086/suporte-vsa/api/tarefas/usuario?email=rafaelpointblank2%40hotmail.com
```

## ✅ SOLUÇÃO - Recompilar e Reiniciar Backend

### Opção 1: Usando Script PowerShell (Recomendado)

1. **Pare o backend atual** (se estiver rodando):
   - Pressione `Ctrl + C` no terminal onde o backend está executando

2. **Execute o script de recompilação**:
   ```powershell
   cd "c:\Users\Rafael-CDS\Desktop\Nova pasta (3)\suporte-back"
   .\recompilar-backend.ps1
   ```

3. **Inicie o backend novamente**:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

### Opção 2: Comandos Manuais

1. **Pare o backend atual** (Ctrl + C)

2. **Navegue até a pasta do backend**:
   ```powershell
   cd "c:\Users\Rafael-CDS\Desktop\Nova pasta (3)\suporte-back"
   ```

3. **Limpe e recompile**:
   ```powershell
   .\mvnw.cmd clean compile
   ```

4. **Reinicie a aplicação**:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

## 📋 Verificação Após Recompilação

Após reiniciar o backend, verifique se:

1. ✅ O backend iniciou sem erros
2. ✅ A porta 8086 está disponível
3. ✅ Acesse: http://localhost:8086/suporte-vsa/api/tarefas/usuario?email=rafaelpointblank2@hotmail.com
4. ✅ Deve retornar JSON com as tarefas (ou array vazio `[]` se não houver tarefas)

## 🔍 Verificando se o Problema Foi Resolvido

No frontend, abra:
- **Página do Usuário**: http://localhost:3000/formularios/tarefas
- **Página do Admin**: http://localhost:3000/formularios/tarefas/gerenciar

Deve carregar sem erros no console!

## 📝 Mudanças Implementadas

### Backend (`TarefaController.java`)
```java
// ANTES (causava erro 400 com @)
@GetMapping("/usuario/{email}")
public ResponseEntity<List<TarefaDTO>> buscarTarefasDoUsuario(@PathVariable String email)

// DEPOIS (funciona corretamente)
@GetMapping("/usuario")
public ResponseEntity<List<TarefaDTO>> buscarTarefasDoUsuario(@RequestParam String email)
```

### Frontend (`tarefaService.ts`)
```typescript
// ANTES
get(`/api/tarefas/usuario/${email}`)

// DEPOIS
get('/api/tarefas/usuario', { params: { email } })
```

## 🎯 Endpoints Atualizados

Todos esses endpoints foram convertidos para usar query parameters:

1. ✅ `/api/tarefas/usuario?email=...` - Buscar tarefas do usuário
2. ✅ `/api/tarefas/criadas-por?email=...` - Buscar tarefas criadas pelo admin
3. ✅ `/api/tarefas/usuario/status?email=...&status=...` - Buscar por status
4. ✅ `/api/tarefas/usuario/vencidas?email=...` - Buscar tarefas vencidas

## ⚠️ Importante

**NÃO ESQUEÇA** de:
1. Parar o backend antes de recompilar
2. Aguardar a compilação terminar completamente
3. Verificar se não há erros de compilação
4. Iniciar o backend novamente

## 🆘 Se o Problema Persistir

1. Verifique se o backend está realmente usando a porta 8086
2. Confirme que não há múltiplas instâncias do backend rodando
3. Limpe o cache do navegador (Ctrl + Shift + Delete)
4. Recarregue a página do frontend (Ctrl + F5)
5. Verifique os logs do backend para erros específicos
