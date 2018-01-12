package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteProfileAttachmentCommand {
    public void run(UUID profileId, UUID id) {

    }
}
