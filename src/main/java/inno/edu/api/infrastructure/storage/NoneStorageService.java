package inno.edu.api.infrastructure.storage;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@ConditionalOnProperty(name = "application.storage.mode", havingValue = "none")
public class NoneStorageService implements StorageService {
    @Override
    public void save(UUID keyId, MultipartFile file) {
        // Do nothing
    }
}