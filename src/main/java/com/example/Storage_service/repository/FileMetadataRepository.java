package com.example.Storage_service.repository;

import com.example.Storage_service.entity.FileMetadata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long>{

    public List<FileMetadata> findByUsername(String username);

}
