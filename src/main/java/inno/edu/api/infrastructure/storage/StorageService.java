package inno.edu.api.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {
    void save(UUID keyId, MultipartFile file);
}
