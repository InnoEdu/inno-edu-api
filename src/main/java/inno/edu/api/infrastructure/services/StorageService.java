package inno.edu.api.infrastructure.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {
    void save(UUID keyId, MultipartFile file);
}
