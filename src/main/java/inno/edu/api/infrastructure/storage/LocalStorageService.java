package inno.edu.api.infrastructure.storage;

import inno.edu.api.infrastructure.configuration.properties.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.util.StringUtils.cleanPath;

@Service
@ConditionalOnProperty(name = "application.storage.mode", havingValue = "local")
public class LocalStorageService implements StorageService {
    @Autowired
    private ApplicationConfiguration configuration;

    @Override
    public void save(UUID keyId, MultipartFile file) {
        String filename = cleanPath(file.getOriginalFilename());

        Path fileLocation = get(configuration.getStorage().getLocation())
                .resolve(filename);

        try {
            createDirectories(fileLocation);
            copy(file.getInputStream(), fileLocation, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
