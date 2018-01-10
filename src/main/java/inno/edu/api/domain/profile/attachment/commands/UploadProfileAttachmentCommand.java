package inno.edu.api.domain.profile.attachment.commands;

import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import inno.edu.api.domain.profile.attachment.commands.mappers.UploadProfileAttachmentRequestMapper;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.attachment.repositories.ProfileAttachmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import inno.edu.api.infrastructure.storage.StorageService;

@Command
public class UploadProfileAttachmentCommand {
    private final ProfileExistsAssertion profileExistsAssertion;

    private final UUIDGeneratorService uuidGeneratorService;
    private final UploadProfileAttachmentRequestMapper uploadProfileAttachmentRequestMapper;
    private final StorageService storageService;
    private final ProfileAttachmentRepository profileAttachmentRepository;

    public UploadProfileAttachmentCommand(ProfileExistsAssertion profileExistsAssertion, UUIDGeneratorService uuidGeneratorService, UploadProfileAttachmentRequestMapper uploadProfileAttachmentRequestMapper, StorageService storageService, ProfileAttachmentRepository profileAttachmentRepository) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.uuidGeneratorService = uuidGeneratorService;
        this.uploadProfileAttachmentRequestMapper = uploadProfileAttachmentRequestMapper;
        this.storageService = storageService;
        this.profileAttachmentRepository = profileAttachmentRepository;
    }

    public ProfileAttachment run(UploadProfileAttachmentRequest request) {
        profileExistsAssertion.run(request.getProfileId());

        String fileName = storageService.save(request.getProfileId(), request.getFile());

        ProfileAttachment profileAttachment = uploadProfileAttachmentRequestMapper.toProfileAttachment(request, fileName);
        profileAttachment.setId(uuidGeneratorService.generate());
        return profileAttachmentRepository.save(profileAttachment);
    }
}
