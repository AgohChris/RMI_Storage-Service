package com.example.Storage_service.service;


import com.example.Storage_service.entity.FileMetadata;
import com.example.Storage_service.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class FileStorageService {
    @Value("${file.upload-dir}")
    private String storageLocation;

    private final FileMetadataRepository fileMetadataRepository;

    public FileStorageService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public void storeFile(MultipartFile file, String username) throws IOException {
        Path userDirectory = Paths.get(storageLocation, username);
        Files.createDirectories(userDirectory);

        Path filePath = userDirectory.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileMetadata metadata = new FileMetadata();
        metadata.setFilePath(filePath.toString());
        metadata.setFileSize(file.getSize());
        metadata.setUploadTimesTamp(LocalDateTime.now());
        metadata.setUsername(username);

        fileMetadataRepository.save(metadata);
    }

    public List<FileMetadata> getFileMetadata(String username) {
        return fileMetadataRepository.findByUsername(username);
    }
}
