package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.attachment.commands.dtos.CreateAttachmentRequest;
import inno.edu.api.domain.attachment.models.Attachment;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class CreateProfileAttachmentCommand {
    public Attachment run(UUID profileId, CreateAttachmentRequest request) {
        return null;
    }
}
