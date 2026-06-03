package iam.solucoesdigitais.baseappjwt.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import iam.solucoesdigitais.baseappjwt.exception.CustomException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Classe para resposta de erro padronizada
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private Map<String, String> details;
        
        public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, Map<String, String> details) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.details = details;
        }
        
        // Getters
        public LocalDateTime getTimestamp() { return timestamp; }
        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
        public Map<String, String> getDetails() { return details; }
    }

    // 1. Tratamento para validações do Bean Validation (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação",
            "Dados de entrada inválidos",
            errors
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    // 2. Tratamento para ConstraintViolationException (validações de parâmetros)
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(javax.validation.ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação",
            "Parâmetros inválidos",
            errors
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 3. Tratamento para HttpMessageNotReadableException (JSON inválido)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String mensagem = "Erro na leitura do JSON. Verifique os dados enviados.";
        
        System.out.println("=== DEBUG HttpMessageNotReadableException ===");
        System.out.println("Mensagem: " + ex.getMessage());
        System.out.println("Causa: " + ex.getCause());
        
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = ife.getPath().get(0).getFieldName();
            System.out.println("Campo com erro: " + fieldName);
            System.out.println("Valor inválido: " + ife.getValue());
            System.out.println("Tipo esperado: " + ife.getTargetType());
            
            if ("role".equals(fieldName)) {
                mensagem = "O valor do campo 'role' é inválido. Por favor, forneça um valor entre: ROLE_SECRETARIO, ROLE_SUPORTE, ROLE_USUARIO, ROLE_ADMIN.";
            } else if ("statusMaterial".equals(fieldName)) {
                mensagem = "O valor do campo 'statusMaterial' é inválido. Valores válidos: FUNCIONANDO, DEFEITO, EM_REPARO, SEM_CONSERTO, EM_ESTOQUE.";
            } else if ("statusChamado".equals(fieldName)) {
                mensagem = "O valor do campo 'statusChamado' é inválido. Valores válidos: ABERTO, EM_ATENDIMENTO, FECHADO, FINALIZADO.";
            }
        }

        if (ex.getMessage().contains("Cannot coerce empty String")) {
            mensagem = "Campos não podem estar vazios. Por favor, forneça valores válidos.";
        }

        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de leitura JSON",
            mensagem,
            null
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 4. Tratamento para DataIntegrityViolationException (violação de constraints do BD)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String mensagem = "Ocorreu um erro de integridade de dados. Verifique os dados enviados.";
        
        Throwable cause = ex.getCause();
        if (cause instanceof ConstraintViolationException) {
            ConstraintViolationException constraintEx = (ConstraintViolationException) cause;
            String constraintName = constraintEx.getConstraintName();

            if (constraintName != null) {
                if (constraintName.contains("UK_692bsnqxa8m9fmx7m1yc6hsui")) {
                    mensagem = "O CPF já está em uso. Por favor, forneça um CPF diferente.";
                } else if (constraintName.contains("UK_863n1y3x0jalatoir4325ehal")) {
                    mensagem = "O nome de usuário já está em uso. Por favor, escolha outro.";
                } else if (constraintName.contains("UK_5171l57faosmj8myawaucatdw")) {
                    mensagem = "O email já está em uso. Por favor, forneça outro endereço de email.";
                }
            }
        }

        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de integridade de dados",
            mensagem,
            null
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 5. Tratamento para ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            ex.getStatus().value(),
            ex.getStatus().getReasonPhrase(),
            ex.getReason(),
            null
        );
        
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    // 6. Tratamento para UsernameNotFoundException
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Usuário não encontrado",
            ex.getMessage(),
            null
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 7. Tratamento para AuthenticationException
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.UNAUTHORIZED.value(),
            "Falha na autenticação",
            "Usuário/Senha incorretos",
            null
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // 8. Tratamento para CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de negócio",
            ex.getMessage(),
            null
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 9. Tratamento para exceções de negócio (RuntimeException)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de negócio",
            ex.getMessage(),
            null
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 10. Tratamento genérico para outras exceções
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // Logar o erro completo
        ex.printStackTrace();
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno do servidor",
            "Ocorreu um erro inesperado. Tente novamente mais tarde.",
            null
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}