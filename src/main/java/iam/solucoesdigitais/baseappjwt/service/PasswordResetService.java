package iam.solucoesdigitais.baseappjwt.service;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import iam.solucoesdigitais.baseappjwt.model.PasswordResetToken;
import iam.solucoesdigitais.baseappjwt.model.Usuario;
import iam.solucoesdigitais.baseappjwt.repository.PasswordResetTokenRepository;
import iam.solucoesdigitais.baseappjwt.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class PasswordResetService {

	private final PasswordResetTokenRepository tokenRepository;
	private final UsuarioRepository userRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder; // Adicione o PasswordEncoder aqui

	public PasswordResetService(PasswordResetTokenRepository tokenRepository, UsuarioRepository userRepository,
			EmailService emailService, PasswordEncoder passwordEncoder) { // E injete-o no construtor
		this.tokenRepository = tokenRepository;
		this.userRepository = userRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder; // Inicialize-o
	}

	public void sendPasswordResetEmail(String email) {
	    Usuario user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    String token = Jwts.builder()
	            .setSubject(user.getUsername())
	            .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
	            .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs")
	            .compact();

	    PasswordResetToken resetToken = new PasswordResetToken(token, user, new Date(System.currentTimeMillis() + 3600000));
	    tokenRepository.save(resetToken);

	    //String resetUrl = "http://localhost:3000/redefinir-senha?token=" + token; // URL da sua aplicação Em Produção
	    String resetUrl = "https://frotavsa.iamtec.org/redefinir-senha?token=" + token; // URL da sua aplicação Em Produção
	    //https://saudevsa.com.br/redefinir-senha?token=XYZ
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
	    mailMessage.setTo(user.getEmail());
	    mailMessage.setFrom("contato@iamtec.org");
	    mailMessage.setSubject("Recuperar Senha SISTEMA VISA");
	    mailMessage.setText("Oi, para criar sua nova senha, clique no link abaixo:\n" + resetUrl);

	    emailService.sendEmail(mailMessage);
	}


	public void validatePasswordResetToken(String token) {
		PasswordResetToken resetToken = tokenRepository.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid Token"));

		if (resetToken.getExpiryDate().before(new Date())) {
			throw new RuntimeException("Token expired");
		}
	}

	public void resetPassword(String token, String newPassword) {
        validatePasswordResetToken(token);
        PasswordResetToken resetToken = tokenRepository.findByToken(token).get();
        Usuario user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword)); // Criptografe a senha antes de salvar
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }
}