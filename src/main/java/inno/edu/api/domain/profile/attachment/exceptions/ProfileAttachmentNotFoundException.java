package inno.edu.api.domain.profile.attachment.exceptions;

import java.util.UUID;

public class ProfileAttachmentNotFoundException extends RuntimeException {
    public ProfileAttachmentNotFoundException(UUID attachmentId) {
        super("Could not find profile attachment'" + attachmentId.toString() + "'.");
    }
}