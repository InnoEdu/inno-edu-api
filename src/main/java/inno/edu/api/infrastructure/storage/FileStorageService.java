package inno.edu.api.infrastructure.storage;

import inno.edu.api.infrastructure.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static org.springframework.util.StringUtils.cleanPath;

@Service
public class FileStorageService implements StorageService {
    @Override
    public void save(UUID keyId, MultipartFile file) {
        Path rootLocation = Paths.get("~/Temp/");

        String filename = keyId + "/" + cleanPath(file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
