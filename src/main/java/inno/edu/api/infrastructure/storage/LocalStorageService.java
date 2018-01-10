package inno.edu.api.infrastructure.storage;

import inno.edu.api.infrastructure.configuration.properties.ApplicationConfiguration;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static com.google.common.io.Files.getFileExtension;
import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@ConditionalOnProperty(name = "application.storage.mode", havingValue = "local")
public class LocalStorageService implements StorageService {
    private final ApplicationConfiguration configuration;
    private final UUIDGeneratorService uuidGeneratorService;

    public LocalStorageService(ApplicationConfiguration configuration, UUIDGeneratorService uuidGeneratorService) {
        this.configuration = configuration;
        this.uuidGeneratorService = uuidGeneratorService;
    }

    @Override
    public String save(UUID keyId, MultipartFile file) {
        String filename = uuidGeneratorService.generate().toString()
                + "."
                + getFileExtension(file.getOriginalFilename());

        Path fileLocation = get(configuration.getStorage().getLocation() + "/" + keyId.toString())
                .resolve(filename);

        try {
            createDirectories(fileLocation);
            copy(file.getInputStream(), fileLocation, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileLocation.toString();
    }

    @Override
    public void delete(String file) {
        try {
            deleteIfExists(get(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
