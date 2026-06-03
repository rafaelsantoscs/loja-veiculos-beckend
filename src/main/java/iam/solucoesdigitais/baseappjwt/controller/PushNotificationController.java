package iam.solucoesdigitais.baseappjwt.controller;

import iam.solucoesdigitais.baseappjwt.model.PushSubscription;
import iam.solucoesdigitais.baseappjwt.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PushNotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    @PostMapping("/push-subscriptions")
    public ResponseEntity<?> subscribe(@RequestBody Map<String, Object> subscriptionData, Authentication authentication) {
        try {
            String username = authentication.getName();
            PushSubscription subscription = pushNotificationService.saveSubscription(username, subscriptionData);
            return ResponseEntity.ok().body(Map.of(
                "message", "Subscription salva com sucesso",
                "id", subscription.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Erro ao salvar subscription: " + e.getMessage()
            ));
        }
    }

    @DeleteMapping("/push-subscriptions")
    public ResponseEntity<?> unsubscribe(Authentication authentication) {
        try {
            String username = authentication.getName();
            pushNotificationService.removeSubscription(username);
            return ResponseEntity.ok().body(Map.of(
                "message", "Subscription removida com sucesso"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Erro ao remover subscription: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/push-notifications/send")
    public ResponseEntity<?> sendNotification(@RequestBody Map<String, Object> request, Authentication authentication) {
        try {
            Map<String, Object> notification = (Map<String, Object>) request.get("notification");
            Object usernamesObj = request.get("usernames");
            
            if (usernamesObj instanceof List) {
                List<String> usernames = (List<String>) usernamesObj;
                pushNotificationService.sendNotificationToMultipleUsers(usernames, notification);
                return ResponseEntity.ok().body(Map.of(
                    "message", "Notificações enviadas com sucesso",
                    "count", usernames.size()
                ));
            } else if (usernamesObj instanceof String) {
                String username = (String) usernamesObj;
                pushNotificationService.sendNotification(username, notification);
                return ResponseEntity.ok().body(Map.of(
                    "message", "Notificação enviada com sucesso"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Campo 'usernames' deve ser uma string ou lista de strings"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Erro ao enviar notificações: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/push-notifications/vapid-public-key")
    public ResponseEntity<?> getVapidPublicKey() {
        return ResponseEntity.ok().body(Map.of(
            "publicKey", pushNotificationService.getVapidPublicKey()
        ));
    }

    @PostMapping("/push-subscriptions/send-targeted")
    public ResponseEntity<?> sendTargetedNotification(@RequestBody Map<String, Object> request, Authentication authentication) {
        try {
            String targetType = (String) request.get("targetType");
            String title = (String) request.get("title");
            String message = (String) request.get("message");
            String icon = (String) request.get("icon");
            String url = (String) request.get("url");
            String tag = (String) request.get("tag");
            
            Map<String, Object> notification = Map.of(
                "title", title != null ? title : "",
                "body", message != null ? message : "",
                "icon", icon != null ? icon : "/icons/icon-192x192.png",
                "data", Map.of(
                    "url", url != null ? url : "",
                    "tag", tag != null ? tag : ""
                )
            );

            int sentCount = 0;

            switch (targetType) {
                case "all":
                    sentCount = pushNotificationService.sendNotificationToAll(notification);
                    break;
                
                case "user":
                case "users":
                    List<Integer> userIds = (List<Integer>) request.get("selectedUsers");
                    if (userIds != null && !userIds.isEmpty()) {
                        sentCount = pushNotificationService.sendNotificationToUserIds(userIds, notification);
                    }
                    break;
                
                case "role":
                    List<String> roles = (List<String>) request.get("selectedRoles");
                    if (roles != null && !roles.isEmpty()) {
                        sentCount = pushNotificationService.sendNotificationToRoles(roles, notification);
                    }
                    break;
                
                default:
                    return ResponseEntity.badRequest().body(Map.of(
                        "error", "Tipo de destinatário inválido: " + targetType
                    ));
            }

            return ResponseEntity.ok().body(Map.of(
                "message", "Notificações enviadas com sucesso",
                "targetType", targetType,
                "sentCount", sentCount
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Erro ao enviar notificações: " + e.getMessage()
            ));
        }
    }
}
