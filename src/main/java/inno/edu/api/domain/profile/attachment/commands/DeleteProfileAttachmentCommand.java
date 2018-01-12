package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.attachment.assertions.AttachmentExistsAssertion;
import inno.edu.api.domain.attachment.commands.DeleteAttachmentCommand;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachmentPrimaryKey;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class DeleteProfileAttachmentCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final AttachmentExistsAssertion attachmentExistsAssertion;
    private final ProfileAttachmentRepository profileAttachmentRepository;
    private final DeleteAttachmentCommand deleteAttachmentCommand;

    public DeleteProfileAttachmentCommand(ProfileExistsAssertion profileExistsAssertion, AttachmentExistsAssertion attachmentExistsAssertion, ProfileAttachmentRepository profileAttachmentRepository, DeleteAttachmentCommand deleteAttachmentCommand) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.attachmentExistsAssertion = attachmentExistsAssertion;
        this.profileAttachmentRepository = profileAttachmentRepository;
        this.deleteAttachmentCommand = deleteAttachmentCommand;
    }

    public void run(UUID profileId, UUID id) {
        profileExistsAssertion.run(profileId);
        attachmentExistsAssertion.run(id);

        ProfileAttachmentPrimaryKey profileAttachmentPrimaryKey =
                ProfileAttachmentPrimaryKey.builder()
                        .profileId(profileId)
                        .attachmentId(id)
                        .build();

        profileAttachmentRepository.delete(profileAttachmentPrimaryKey);

        deleteAttachmentCommand.run(id);
    }
}
