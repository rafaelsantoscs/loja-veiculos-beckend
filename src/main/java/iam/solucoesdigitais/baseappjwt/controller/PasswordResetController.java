package iam.solucoesdigitais.baseappjwt.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iam.solucoesdigitais.baseappjwt.service.PasswordResetService;

@RestController
@RequestMapping("/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        try {
            passwordResetService.sendPasswordResetEmail(email);
            return ResponseEntity.ok("Password reset email sent.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending password reset email.");
        }
    }


//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> payload) {
//        String email = payload.get("email");
//        try {
//            passwordResetService.sendPasswordResetEmail(email);
//            return ResponseEntity.ok("Password reset email sent.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error sending password reset email.");
//        }
//    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");
        try {
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Senha Atualizada com Sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao Redefinir Senha.");
        }
    }
    
//    @GetMapping("/reset")
//    public ResponseEntity<String> showResetForm(@RequestParam("token") String token) {
//        // Gerar HTML dinamicamente
//        String htmlContent = "<!DOCTYPE html>" +
//                "<html>" +
//                "<head>" +
//                "<meta http-equiv='refresh' content='0;url=cliquesaude://reset-password?token=" + token + "' />" +
//                "<title>Redirecionando...</title>" +
//                "</head>" +
//                "<body>" +
//                "<p>Se você não for redirecionado automaticamente, <a href='cliquesaude://reset-password?token=" + token + "'>clique aqui</a>.</p>" +
//                "</body>" +
//                "</html>";
//
//        // Retornar o HTML gerado
//        return ResponseEntity.ok().body(htmlContent);
//    }
    
//    @GetMapping("/reset")
//    public ResponseEntity<String> showResetForm(@RequestParam("token") String token) {
//        // HTML com formulário de redefinição de senha
//    	String htmlContent = "<!DOCTYPE html>" +
//    		    "<html>" +
//    		    "<head>" +
//    		    "<title>Redefinir Senha</title>" +
//    		    "<style>" +
//    		    "body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; font-family: Arial, sans-serif; }" +
//    		    "form { display: flex; flex-direction: column; width: 300px; padding: 20px; border: 1px solid #ccc; border-radius: 5px; background-color: #f9f9f9; }" +
//    		    "h2 { text-align: center; margin-bottom: 20px; }" +
//    		    "h3 { text-align: center; margin-bottom: 20px; }" +
//    		    "label { margin-bottom: 10px; }" +
//    		    "input { padding: 10px; margin-bottom: 15px; border-radius: 3px; border: 1px solid #ccc; }" +
//    		    "button { padding: 10px; border-radius: 3px; border: none; background-color: #4CAF50; color: white; font-size: 16px; cursor: pointer; }" +
//    		    "button:hover { background-color: #45a049; }" +
//    		    "</style>" +
//    		    "</head>" +
//    		    "<body>" +
//    		    "<form action='/clique-saude/password/reset' method='POST'>" +
//    		    "<h2>Clique Saúde</h2>" +
//    		    "<h3>Redefinir Senha</h3>" +
//    		    "<input type='hidden' name='token' value='" + token + "' />" +
//    		    "<label for='password'>Nova Senha:</label>" +
//    		    "<input type='password' id='password' name='password' required/>" +
//    		    "<label for='confirmPassword'>Confirme a Nova Senha:</label>" +
//    		    "<input type='password' id='confirmPassword' name='confirmPassword' required/>" +
//    		    "<button type='submit'>Redefinir Senha</button>" +
//    		    "</form>" +
//    		    "</body>" +
//    		    "</html>";
//
//
//        return ResponseEntity.ok().body(htmlContent);
//    }

//    @PostMapping("/reset")
//    public ResponseEntity<String> processResetForm(@RequestParam("token") String token,
//                                                   @RequestParam("password") String password,
//                                                   @RequestParam("confirmPassword") String confirmPassword) {
//        if (!password.equals(confirmPassword)) {
//            // Mensagem de erro para senhas não coincidentes
//            String htmlContent = "<!DOCTYPE html>" +
//                    "<html>" +
//                    "<head>" +
//                    "<title>Erro</title>" +
//                    "<style>" +
//                    "body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; font-family: Arial, sans-serif; }" +
//                    ".message { text-align: center; padding: 20px; border: 1px solid #e74c3c; border-radius: 5px; background-color: #f8d7da; color: #e74c3c; width: 300px; }" +
//                    "</style>" +
//                    "</head>" +
//                    "<body>" +
//                    "<div class='message'>" +
//                    "<p>As senhas não coincidem. Por favor, tente novamente.</p>" +
//                    "<a href='/clique-saude/password/reset?token=" + token + "'>Voltar ao formulário</a>" +
//                    "</div>" +
//                    "</body>" +
//                    "</html>";
//            return ResponseEntity.badRequest().body(htmlContent);
//        }
//
//        try {
//            passwordResetService.resetPassword(token, password);
//            // Mensagem de sucesso após redefinir a senha
//            String htmlContent = "<!DOCTYPE html>" +
//                    "<html>" +
//                    "<head>" +
//                    "<title>Sucesso</title>" +
//                    "<style>" +
//                    "body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; font-family: Arial, sans-serif; }" +
//                    ".message { text-align: center; padding: 20px; border: 1px solid #2ecc71; border-radius: 5px; background-color: #d4edda; color: #2ecc71; width: 300px; }" +
//                    "</style>" +
//                    "</head>" +
//                    "<body>" +
//                    "<div class='message'>" +
//                    "<p>Sua senha foi redefinida com sucesso! Já pode fazer o Login.</p>" +
//                    "</div>" +
//                    "</body>" +
//                    "</html>";
//            return ResponseEntity.ok(htmlContent);
//        } catch (Exception e) {
//            // Mensagem de erro para token inválido ou expirado
//            String htmlContent = "<!DOCTYPE html>" +
//                    "<html>" +
//                    "<head>" +
//                    "<title>Token Inválido</title>" +
//                    "<style>" +
//                    "body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; font-family: Arial, sans-serif; }" +
//                    ".message { text-align: center; padding: 20px; border: 1px solid #e74c3c; border-radius: 5px; background-color: #f8d7da; color: #e74c3c; width: 300px; }" +
//                    "</style>" +
//                    "</head>" +
//                    "<body>" +
//                    "<div class='message'>" +
//                    "<p>Token inválido ou expirado.</p>" +
//                    "<a href='/clique-saude/password/reset?token=" + token + "'>Voltar ao formulário</a>" +
//                    "</div>" +
//                    "</body>" +
//                    "</html>";
//            return ResponseEntity.badRequest().body(htmlContent);
//        }
//    }

}