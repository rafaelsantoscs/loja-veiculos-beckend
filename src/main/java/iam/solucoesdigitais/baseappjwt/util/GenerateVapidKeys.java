package iam.solucoesdigitais.baseappjwt.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class GenerateVapidKeys {
    public static void main(String[] args) {
        try {
            // Adicionar o provedor Bouncy Castle
            Security.addProvider(new BouncyCastleProvider());
            
            // Gerar par de chaves VAPID usando EC P-256
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            
            // Converter para base64
            String publicKey = Base64.getUrlEncoder().withoutPadding().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getUrlEncoder().withoutPadding().encodeToString(keyPair.getPrivate().getEncoded());
            
            System.out.println("=== NOVAS CHAVES VAPID ===");
            System.out.println();
            System.out.println("Chave Pública (para frontend):");
            System.out.println(publicKey);
            System.out.println();
            System.out.println("Chave Privada (para backend):");
            System.out.println(privateKey);
            System.out.println();
            System.out.println("=== CONFIGURAÇÃO PARA PRODUÇÃO ===");
            System.out.println();
            System.out.println("Configure essas variáveis de ambiente ao executar o JAR:");
            System.out.println("export PUSH_VAPID_PUBLIC_KEY=\"" + publicKey + "\"");
            System.out.println("export PUSH_VAPID_PRIVATE_KEY=\"" + privateKey + "\"");
            System.out.println("export PUSH_VAPID_SUBJECT=\"mailto:admin@frotavsa.iamtec.org\"");
            System.out.println();
            System.out.println("Ou execute o JAR com:");
            System.out.println("java -DPUSH_VAPID_PUBLIC_KEY=\"" + publicKey + "\" \\");
            System.out.println("     -DPUSH_VAPID_PRIVATE_KEY=\"" + privateKey + "\" \\");
            System.out.println("     -DPUSH_VAPID_SUBJECT=\"mailto:admin@frotavsa.iamtec.org\" \\");
            System.out.println("     -jar frota-vsa-0.0.1-SNAPSHOT.jar");
            
        } catch (Exception e) {
            System.err.println("Erro ao gerar chaves VAPID: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
