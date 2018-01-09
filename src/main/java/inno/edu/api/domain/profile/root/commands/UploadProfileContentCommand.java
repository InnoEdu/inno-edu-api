package inno.edu.api.domain.profile.root.commands;

import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.storage.StorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Command
public class UploadProfileContentCommand {
    private StorageService storageService;

    public void run(UUID id, MultipartFile file) {
        storageService.save(id, file);
    }
}
