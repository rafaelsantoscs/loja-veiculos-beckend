package iam.solucoesdigitais.baseappjwt.service;

import iam.solucoesdigitais.baseappjwt.model.PushSubscription;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.repository.PushSubscriptionRepository;
import iam.solucoesdigitais.baseappjwt.repository.UsuarioRepository;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.security.Security;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PushNotificationService {

    @Autowired
    private PushSubscriptionRepository pushSubscriptionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Estas chaves VAPID devem ser configuradas nas propriedades da aplicação
    @Value("${push.vapid.public.key:BEDENAz3fGhIJE53o3iHk1okSh6Rzzc8tVKqAuoDesDJCaL8iEWpHWnxHWp4_A5bKvxYOe5OVvNQLLJ9595hXbM}")
    private String vapidPublicKey;

    @Value("${push.vapid.private.key:MM7fdQ8umpxb3pS85imvtFBP646ZB2U0WUKzJDOouXc}")
    private String vapidPrivateKey;

    @Value("${push.vapid.subject:mailto:admin@frotavsa.iamtec.org}")
    private String vapidSubject;

    private PushService pushService;

    @PostConstruct
    private void init() {
        try {
            System.out.println("🔧 ========== INICIALIZANDO PUSHSERVICE ==========");
            System.out.println("📋 Chave pública VAPID COMPLETA: " + vapidPublicKey);
            System.out.println("🔑 Chave privada VAPID COMPLETA: " + vapidPrivateKey);
            System.out.println("📧 Subject VAPID: " + vapidSubject);
            
            System.out.println("🔍 Validando chaves VAPID...");
            System.out.println("� Tamanho da chave pública: " + vapidPublicKey.length());
            System.out.println("� Tamanho da chave privada: " + vapidPrivateKey.length());
            
            if (vapidPublicKey.length() != 87) {
                System.out.println("❌ ERRO: Chave pública tem tamanho incorreto! Esperado: 87, Atual: " + vapidPublicKey.length());
            }
            if (vapidPrivateKey.length() != 43) {
                System.out.println("❌ ERRO: Chave privada tem tamanho incorreto! Esperado: 43, Atual: " + vapidPrivateKey.length());
            }
            
            // Adicionar o provedor Bouncy Castle para criptografia
            Security.addProvider(new BouncyCastleProvider());
            System.out.println("🔐 Bouncy Castle Provider adicionado");
            
            // Inicializar o serviço de push
            System.out.println("🚀 Criando PushService com as chaves...");
            pushService = new PushService(vapidPublicKey, vapidPrivateKey, vapidSubject);
            System.out.println("✅ PushService inicializado com sucesso!");
            System.out.println("🔧 ========== PUSHSERVICE INICIALIZADO ==========");
            
        } catch (Exception e) {
            System.err.println("❌ ERRO CRÍTICO na inicialização do PushService: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao inicializar PushService: " + e.getMessage(), e);
        }
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public PushSubscription saveSubscription(String username, Map<String, Object> subscriptionData) {
        try {
            System.out.println("💾 Salvando nova subscription para usuário: " + username);
            
            String endpoint = (String) subscriptionData.get("endpoint");
            Map<String, String> keys = (Map<String, String>) subscriptionData.get("keys");
            String p256dhKey = keys.get("p256dh");
            String authKey = keys.get("auth");

            System.out.println("📡 Endpoint: " + (endpoint != null ? endpoint.substring(0, Math.min(50, endpoint.length())) + "..." : "null"));
            System.out.println("🔑 P256DH Key: " + (p256dhKey != null ? p256dhKey.substring(0, 20) + "..." : "null"));
            System.out.println("🔐 Auth Key: " + (authKey != null ? authKey.substring(0, 20) + "..." : "null"));

            // Desativar subscriptions antigas do mesmo usuário
            pushSubscriptionRepository.deactivateAllByUsername(username);
            System.out.println("🔄 Subscriptions antigas desativadas para: " + username);

            // Criar nova subscription
            PushSubscription subscription = new PushSubscription(username, endpoint, p256dhKey, authKey);
            
            // Vamos fazer um teste de envio logo após salvar para verificar se funciona
            PushSubscription savedSubscription = pushSubscriptionRepository.save(subscription);
            System.out.println("✅ Subscription salva com status ativo: " + savedSubscription.isActive());
            System.out.println("📋 ID da subscription: " + savedSubscription.getId());
            
            // TESTE: Tentar enviar uma notificação de teste para validar a subscription
            try {
                System.out.println("🧪 Fazendo teste de validação da subscription...");
                Map<String, Object> testNotification = Map.of(
                    "title", "Teste de Conexão",
                    "body", "Subscription configurada com sucesso!",
                    "icon", "/icons/icon-192x192.png"
                );
                sendPushNotification(savedSubscription, testNotification);
                System.out.println("✅ Teste de validação passou - subscription válida!");
            } catch (Exception testError) {
                System.err.println("❌ Teste de validação falhou: " + testError.getMessage());
                // NÃO desativar automaticamente - deixar o usuário decidir
                System.out.println("⚠️ Subscription mantida ativa apesar do teste falhar");
            }
            
            return savedSubscription;
        } catch (Exception e) {
            System.err.println("❌ Erro ao salvar subscription: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar subscription: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void removeSubscription(String username) {
        pushSubscriptionRepository.deactivateAllByUsername(username);
    }

    public void sendNotification(String username, Map<String, Object> notification) {
        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByUsernameAndActiveTrue(username);
        
        for (PushSubscription subscription : subscriptions) {
            try {
                sendPushNotification(subscription, notification);
            } catch (Exception e) {
                System.err.println("Erro ao enviar notificação push para " + username + ": " + e.getMessage());
                // Desativar subscription inválida
                subscription.setActive(false);
                pushSubscriptionRepository.save(subscription);
            }
        }
    }

    public void sendNotificationToMultipleUsers(List<String> usernames, Map<String, Object> notification) {
        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByUsernameInAndActiveTrue(usernames);
        
        for (PushSubscription subscription : subscriptions) {
            try {
                sendPushNotification(subscription, notification);
            } catch (Exception e) {
                System.err.println("Erro ao enviar notificação push para " + subscription.getUsername() + ": " + e.getMessage());
                // Desativar subscription inválida
                subscription.setActive(false);
                pushSubscriptionRepository.save(subscription);
            }
        }
    }

    private void sendPushNotification(PushSubscription subscription, Map<String, Object> notification) {
        System.out.println("🚀 ========== ENVIANDO PUSH NOTIFICATION ==========");
        System.out.println("🚀 Iniciando envio de push notification para: " + subscription.getUsername());
        System.out.println("📡 Endpoint: " + subscription.getEndpoint());
        
        try {
            // Verificar se pushService foi inicializado
            if (pushService == null) {
                System.out.println("❌ ERRO CRÍTICO: PushService é NULL!");
                throw new RuntimeException("PushService não foi inicializado corretamente");
            }
            System.out.println("✅ PushService está inicializado e disponível");
            
            // Log das chaves VAPID que estão sendo utilizadas
            System.out.println("🔍 ========== VERIFICAÇÃO DE CHAVES VAPID ==========");
            System.out.println("🔑 Chave pública em uso: " + vapidPublicKey);
            System.out.println("🔑 Chave privada em uso: " + vapidPrivateKey);
            System.out.println("📧 Subject em uso: " + vapidSubject);
            System.out.println("🔍 ========== FIM VERIFICAÇÃO CHAVES ==========");
            
            // Criar a subscription no formato da biblioteca web-push
            System.out.println("🔧 Criando subscription para web-push...");
            nl.martijndwars.webpush.Subscription webPushSubscription = new nl.martijndwars.webpush.Subscription();
            webPushSubscription.endpoint = subscription.getEndpoint();
            webPushSubscription.keys = new nl.martijndwars.webpush.Subscription.Keys();
            webPushSubscription.keys.p256dh = subscription.getP256dhKey();
            webPushSubscription.keys.auth = subscription.getAuthKey();
            System.out.println("✅ Subscription web-push criada com sucesso");

            // Converter a notificação para JSON
            String payload = convertNotificationToJson(notification);
            System.out.println("📝 Payload JSON: " + payload);

            // Criar e enviar a notificação
            System.out.println("📦 Criando notificação web-push...");
            Notification webPushNotification = new Notification(webPushSubscription, payload);
            System.out.println("✅ Notificação web-push criada com sucesso");
            
            System.out.println("📦 Notificação criada, enviando...");
            System.out.println("🚀 ========== TENTANDO ENVIAR COM PUSHSERVICE ==========");
            
            HttpResponse response = pushService.send(webPushNotification);

            // Log do resultado
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("📊 Status da resposta: " + statusCode);
            System.out.println("✅ ========== PUSH NOTIFICATION ENVIADA COM SUCESSO ==========");
            
            if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
                System.out.println("✅ Notificação enviada com sucesso para: " + subscription.getUsername());
            } else {
                System.err.println("❌ Erro no envio da notificação. Status: " + statusCode);
                System.err.println("❌ Reason: " + response.getStatusLine().getReasonPhrase());
                throw new RuntimeException("Falha no envio: " + response.getStatusLine().getReasonPhrase());
            }

        } catch (Exception e) {
            System.err.println("❌ ========== ERRO DETALHADO PUSH NOTIFICATION ==========");
            System.err.println("❌ ERRO DETALHADO ao enviar push notification:");
            System.err.println("   Usuário: " + subscription.getUsername());
            System.err.println("   Tipo do Erro: " + e.getClass().getSimpleName());
            System.err.println("   Mensagem do Erro: " + e.getMessage());
            System.err.println("🔍 Estado atual das chaves VAPID:");
            System.err.println("   Chave Pública: " + vapidPublicKey);
            System.err.println("   Chave Privada: " + vapidPrivateKey);
            System.err.println("   Subject: " + vapidSubject);
            System.err.println("🔍 PushService: " + (pushService != null ? "INICIALIZADO" : "NULL"));
            System.err.println("❌ ========== FIM ERRO DETALHADO ==========");
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar push notification", e);
        }
    }

    @SuppressWarnings("unchecked")
    private String convertNotificationToJson(Map<String, Object> notification) {
        try {
            System.out.println("🔧 Convertendo notificação para JSON com configurações otimizadas para dispositivos móveis");
            
            // Criar um JSON estruturado para a notificação com configurações mobile-friendly
            StringBuilder json = new StringBuilder();
            json.append("{");
            
            // Campos básicos
            json.append("\"title\":\"").append(notification.get("title") != null ? notification.get("title") : "Frota VSA").append("\",");
            json.append("\"body\":\"").append(notification.get("body") != null ? notification.get("body") : "Nova mensagem").append("\",");
            json.append("\"icon\":\"").append(notification.get("icon") != null ? notification.get("icon") : "/icons/icon-192x192.png").append("\",");
            json.append("\"badge\":\"").append(notification.get("badge") != null ? notification.get("badge") : "/icons/icon-192x192.png").append("\",");
            
            // Configurações específicas para melhorar exibição em dispositivos móveis
            json.append("\"tag\":\"frota-notification-").append(System.currentTimeMillis()).append("\",");
            json.append("\"timestamp\":").append(System.currentTimeMillis()).append(",");
            json.append("\"requireInteraction\":false,"); // Melhor para mobile
            json.append("\"silent\":false,");
            json.append("\"renotify\":true,"); // Força renotificação
            
            // Data personalizada
            if (notification.get("data") != null) {
                Map<String, Object> data = (Map<String, Object>) notification.get("data");
                json.append("\"data\":{");
                boolean first = true;
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    if (!first) json.append(",");
                    json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue() != null ? entry.getValue() : "").append("\"");
                    first = false;
                }
                json.append("}");
            } else {
                json.append("\"data\":{\"timestamp\":\"").append(System.currentTimeMillis()).append("\"}");
            }
            
            json.append("}");
            
            String jsonString = json.toString();
            System.out.println("✅ JSON da notificação criado: " + jsonString);
            return jsonString;
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao converter notificação para JSON: " + e.getMessage());
            e.printStackTrace();
            
            // Retornar um JSON básico e compatível em caso de erro
            String fallbackJson = "{" +
                "\"title\":\"" + (notification.get("title") != null ? notification.get("title") : "Frota VSA") + "\"," +
                "\"body\":\"" + (notification.get("body") != null ? notification.get("body") : "Nova mensagem") + "\"," +
                "\"icon\":\"/icons/icon-192x192.png\"," +
                "\"badge\":\"/icons/icon-192x192.png\"," +
                "\"tag\":\"frota-fallback-" + System.currentTimeMillis() + "\"," +
                "\"timestamp\":" + System.currentTimeMillis() + "," +
                "\"requireInteraction\":false," +
                "\"renotify\":true" +
                "}";
            
            System.out.println("🔄 Usando JSON de fallback: " + fallbackJson);
            return fallbackJson;
        }
    }

    // Método auxiliar para gerar Application Server Key
    public String getVapidPublicKey() {
        return vapidPublicKey;
    }

    // Novos métodos para notificações específicas

    public int sendNotificationToAll(Map<String, Object> notification) {
        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByActiveTrue();
        int sentCount = 0;
        
        for (PushSubscription subscription : subscriptions) {
            try {
                sendPushNotification(subscription, notification);
                sentCount++;
            } catch (Exception e) {
                System.err.println("Erro ao enviar notificação push para " + subscription.getUsername() + ": " + e.getMessage());
                subscription.setActive(false);
                pushSubscriptionRepository.save(subscription);
            }
        }
        
        return sentCount;
    }

    public int sendNotificationToUserIds(List<Integer> userIds, Map<String, Object> notification) {
        System.out.println("🔍 ========== DEBUG CONVERSÃO IDS PARA USERNAMES ==========");
        System.out.println("📋 User IDs recebidos: " + userIds);
        
        // Converter IDs para usernames
        List<Usuario> usuarios = usuarioRepository.findAllById(userIds.stream().map(Long::valueOf).collect(Collectors.toList()));
        System.out.println("👥 Usuários encontrados: " + usuarios.size());
        for (Usuario user : usuarios) {
            System.out.println("   - ID: " + user.getId() + ", Username: " + user.getUsername());
        }
        
        List<String> usernames = usuarios.stream().map(Usuario::getUsername).collect(Collectors.toList());
        System.out.println("📝 Usernames extraídos: " + usernames);
        
        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByUsernameInAndActiveTrue(usernames);
        System.out.println("📡 Subscriptions ativas encontradas: " + subscriptions.size());
        for (PushSubscription sub : subscriptions) {
            System.out.println("   - Username: " + sub.getUsername() + ", ID: " + sub.getId() + ", Endpoint: " + sub.getEndpoint().substring(0, Math.min(50, sub.getEndpoint().length())) + "...");
        }
        System.out.println("🔍 ========== FIM DEBUG CONVERSÃO ==========");
        
        int sentCount = 0;
        
        for (PushSubscription subscription : subscriptions) {
            try {
                sendPushNotification(subscription, notification);
                sentCount++;
            } catch (Exception e) {
                System.err.println("Erro ao enviar notificação push para " + subscription.getUsername() + ": " + e.getMessage());
                subscription.setActive(false);
                pushSubscriptionRepository.save(subscription);
            }
        }
        
        return sentCount;
    }

    public int sendNotificationToRoles(List<String> roles, Map<String, Object> notification) {
        // Buscar usuários com as roles especificadas
        List<Usuario> usuarios = usuarioRepository.findByRolesIn(roles);
        List<String> usernames = usuarios.stream().map(Usuario::getUsername).collect(Collectors.toList());
        
        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByUsernameInAndActiveTrue(usernames);
        int sentCount = 0;
        
        for (PushSubscription subscription : subscriptions) {
            try {
                sendPushNotification(subscription, notification);
                sentCount++;
            } catch (Exception e) {
                System.err.println("Erro ao enviar notificação push para " + subscription.getUsername() + ": " + e.getMessage());
                subscription.setActive(false);
                pushSubscriptionRepository.save(subscription);
            }
        }
        
        return sentCount;
    }
}
