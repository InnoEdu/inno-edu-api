package inno.edu.api.domain.attachment.exceptions;

import java.util.UUID;

public class AttachmentNotFoundException extends RuntimeException {
    public AttachmentNotFoundException(UUID attachmentId) {
        super("Could not find profile attachment'" + attachmentId.toString() + "'.");
    }
}