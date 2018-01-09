package inno.edu.api.infrastructure.storage;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.springframework.util.StringUtils.cleanPath;

@Service
@ConditionalOnProperty(name = "application.storage.mode", havingValue = "local")
public class LocalStorageService implements StorageService {
    @Override
    public void save(UUID keyId, MultipartFile file) {
        Path rootLocation = Paths.get("");

        String filename = cleanPath(file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
//            Files.copy(inputStream, rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
