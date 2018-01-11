package inno.edu.api.domain.profile.attachment.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.profile.attachment.exceptions.ProfileAttachmentNotFoundException;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class ProfileAttachmentExistsAssertion extends EntityExistsAssertion<ProfileAttachmentRepository, ProfileAttachmentNotFoundException, Function<UUID, ProfileAttachmentNotFoundException>> {
    protected ProfileAttachmentExistsAssertion(ProfileAttachmentRepository repository) {
        super(repository, ProfileAttachmentNotFoundException::new);
    }
}