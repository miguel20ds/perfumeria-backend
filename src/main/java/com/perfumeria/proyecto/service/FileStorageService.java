package com.perfumeria.proyecto.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service

public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String guardarImagen(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = getExtension(file.getOriginalFilename());
        String nombreArchivo = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(nombreArchivo);
        Files.copy(file.getInputStream(), filePath);

        return nombreArchivo;
    }

    private String getExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return ".jpg";
    }
}
