package com.example.inventory.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    // Mengambil path folder dari application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    // Method untuk menyimpan file
    public String storeFile(MultipartFile file) throws IOException {
        // Cek jika folder uploads belum ada, maka buat baru
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate nama file unik agar tidak bentrok 
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        // Proses copy file ke folder tujuan
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return newFileName; 
        } catch (IOException e) {
            throw new IOException("Gagal menyimpan file: " + originalFileName, e);
        }
    }
}