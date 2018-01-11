package inno.edu.api.domain.attachment.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.attachment.exceptions.AttachmentNotFoundException;
import inno.edu.api.domain.attachment.repositories.AttachmentRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class AttachmentExistsAssertion extends EntityExistsAssertion<AttachmentRepository, AttachmentNotFoundException, Function<UUID, AttachmentNotFoundException>> {
    protected AttachmentExistsAssertion(AttachmentRepository repository) {
        super(repository, AttachmentNotFoundException::new);
    }
}