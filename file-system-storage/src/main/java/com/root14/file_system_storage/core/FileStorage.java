package com.root14.file_system_storage.core;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileStorage {
    public void saveFile(MultipartFile file, String directoryPath) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File cannot be null.");
        }

        Path dirPath = Path.of(directoryPath);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IOException("Invalid file name.");
        }

        Path filePath = dirPath.resolve(originalFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public MultipartFile convertToMultipartFile(byte[] fileData, String fileName) throws Exception {
        String fileExtension = getFileExtension(fileName);

        String contentType = "application/octet-stream";
        if (fileExtension.equalsIgnoreCase(".rep")) {
            contentType = "application/octet-stream";
        } else if (fileExtension.equalsIgnoreCase(".json")) {
            contentType = "application/json";
        }

        return new CustomMultipartFile(fileData, fileName, contentType);
    }

    public String getFileExtension(String fileName) {
        if (fileName.lastIndexOf('.') > 0) {
            return fileName.substring(fileName.lastIndexOf('.'));
        }
        return "";
    }

    public MultipartFile readFile(String directoryPath, String filename) throws Exception {
        Path path = Path.of(directoryPath).resolve(filename);
        byte[] fileData = Files.readAllBytes(path);
        return convertToMultipartFile(fileData, filename);
    }
}
