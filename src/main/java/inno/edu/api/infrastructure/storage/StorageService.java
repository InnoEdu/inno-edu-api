package inno.edu.api.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {
    String save(UUID keyId, MultipartFile file);
}
