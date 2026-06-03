package iam.solucoesdigitais.baseappjwt.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    // Diretório para uploads - configurado para funcionar tanto em Windows quanto Linux
    private static final String UPLOAD_DIR = System.getProperty("os.name").toLowerCase().contains("windows") 
        ? "C:/uploads/responsavel-tecnico/" 
        : "/var/uploads/responsavel-tecnico/";

    @PostMapping("/responsavel-tecnico-documento")
    public ResponseEntity<Map<String, Object>> uploadDocumentoResponsavelTecnico(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "empresaId", required = false) String empresaId,
            @RequestParam(value = "cnpjCpf", required = false) String cnpjCpf,
            Authentication authentication) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validações
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "Arquivo não selecionado");
                return ResponseEntity.badRequest().body(response);
            }

            // Validar tipo de arquivo (apenas PDF)
            String contentType = file.getContentType();
            if (!"application/pdf".equals(contentType)) {
                response.put("success", false);
                response.put("message", "Apenas arquivos PDF são permitidos");
                return ResponseEntity.badRequest().body(response);
            }

            // Validar tamanho do arquivo (máximo 3MB)
            if (file.getSize() > 3 * 1024 * 1024) {
                response.put("success", false);
                response.put("message", "Arquivo muito grande. Máximo permitido: 3MB");
                return ResponseEntity.badRequest().body(response);
            }

            // Criar diretório se não existir
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Gerar nome único para o arquivo
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String uniqueId = UUID.randomUUID().toString().substring(0, 8);
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            
            // Construir nome do arquivo com informações da empresa
            StringBuilder fileName = new StringBuilder("RT_");
            fileName.append(timestamp).append("_").append(uniqueId);
            
            // Adicionar ID da empresa se fornecido
            if (empresaId != null && !empresaId.trim().isEmpty()) {
                fileName.append("_EMP").append(empresaId);
            }
            
            // Adicionar CNPJ/CPF limpo se fornecido
            if (cnpjCpf != null && !cnpjCpf.trim().isEmpty()) {
                String cnpjCpfLimpo = cnpjCpf.replaceAll("[^0-9]", ""); // Remove caracteres especiais
                if (!cnpjCpfLimpo.isEmpty()) {
                    fileName.append("_DOC").append(cnpjCpfLimpo);
                }
            }
            
            String newFilename = fileName.toString() + fileExtension;

            // Salvar arquivo
            Path filePath = Paths.get(UPLOAD_DIR + newFilename);
            Files.write(filePath, file.getBytes());

            // URL para acesso ao arquivo
            String fileUrl = "/uploads/responsavel-tecnico/" + newFilename;

            response.put("success", true);
            response.put("message", "Arquivo enviado com sucesso");
            response.put("data", Map.of(
                "filename", newFilename,
                "originalFilename", originalFilename,
                "url", fileUrl,
                "size", file.getSize()
            ));

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Erro ao salvar arquivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro interno do servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/responsavel-tecnico-documento")
    public ResponseEntity<Map<String, Object>> deleteDocumentoResponsavelTecnico(
            @RequestParam("filename") String filename,
            Authentication authentication) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar nome do arquivo
            if (filename == null || filename.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Nome do arquivo não informado");
                return ResponseEntity.badRequest().body(response);
            }

            // Remover arquivo
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                response.put("success", true);
                response.put("message", "Arquivo removido com sucesso");
            } else {
                response.put("success", false);
                response.put("message", "Arquivo não encontrado");
            }

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Erro ao remover arquivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro interno do servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/responsavel-tecnico-documento/{filename}")
    public ResponseEntity<Resource> downloadDocumentoResponsavelTecnico(
            @PathVariable String filename,
            Authentication authentication) {
        
        try {
            // Validar o nome do arquivo para evitar path traversal
            if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
                return ResponseEntity.badRequest().build();
            }

            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/pdf";
                }

                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
