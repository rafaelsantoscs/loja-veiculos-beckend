package iam.solucoesdigitais.baseappjwt.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import iam.solucoesdigitais.baseappjwt.config.UploadConfig;

@Service
public class StorageService {

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * Salva o arquivo e retorna apenas o nome gerado.
     */
    public String salvarArquivo(Long veiculoId, MultipartFile file) throws IOException {
        return salvarArquivo("veiculos", veiculoId, file);
    }

    /**
     * Versão genérica: salva em {base}/{subPasta}/{id}/.
     */
    public String salvarArquivo(String subPasta, Long id, MultipartFile file) throws IOException {

        Path pasta = Paths.get(
                uploadConfig.getUploadVeiculosDir(),
                subPasta,
                id.toString());

        Files.createDirectories(pasta);

        String extensao = "";

        String nomeOriginal = file.getOriginalFilename();

        if (nomeOriginal != null && nomeOriginal.contains(".")) {
            extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
        }

        String nomeArquivo = UUID.randomUUID() + extensao;

        Path destino = pasta.resolve(nomeArquivo);

        Files.copy(
                file.getInputStream(),
                destino,
                StandardCopyOption.REPLACE_EXISTING);

        return nomeArquivo;
    }

    /**
     * Exclui um arquivo do disco.
     */
    public void excluirArquivo(Long veiculoId, String nomeArquivo) throws IOException {

        Path caminho = Paths.get(
                uploadConfig.getUploadVeiculosDir(),
                "veiculos",
                veiculoId.toString(),
                nomeArquivo);

        Files.deleteIfExists(caminho);
    }

    /**
     * Monta a URL pública da imagem.
     */
    public String montarUrl(Long veiculoId, String nomeArquivo) {
        return montarUrl("veiculos", veiculoId, nomeArquivo);
    }

    /**
     * Versão genérica: URL pública em {baseUrl}/{subPasta}/{id}/{arquivo}.
     */
    public String montarUrl(String subPasta, Long id, String nomeArquivo) {

        return uploadConfig.getBaseUrl()
                + "/" + subPasta + "/"
                + id
                + "/"
                + nomeArquivo;
    }

}