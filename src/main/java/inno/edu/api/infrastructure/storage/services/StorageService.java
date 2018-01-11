package inno.edu.api.infrastructure.storage.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {
    String save(UUID resourceId, MultipartFile file);
    void delete(String file);
}
