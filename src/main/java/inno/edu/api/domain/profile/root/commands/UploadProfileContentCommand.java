package inno.edu.api.domain.profile.root.commands;

import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.StorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Command
public class UploadProfileContentCommand {
    private StorageService storageService;

    public void run(UUID uuid, MultipartFile file) {

    }
}
