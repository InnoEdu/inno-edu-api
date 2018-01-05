package inno.edu.api.domain.profile.association.commands;

import inno.edu.api.domain.profile.association.assertions.ProfileAssociationExistsAssertion;
import inno.edu.api.domain.profile.commands.dtos.RejectProfileAssociationRequest;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static inno.edu.api.domain.profile.association.models.RequestStatus.REJECTED;

@Command
public class RejectProfileAssociationCommand {
    private final ProfileAssociationExistsAssertion profileAssociationExistsAssertion;
    private final ProfileAssociationRepository profileAssociationRepository;

    public RejectProfileAssociationCommand(ProfileAssociationExistsAssertion profileAssociationExistsAssertion, ProfileAssociationRepository profileAssociationRepository) {
        this.profileAssociationExistsAssertion = profileAssociationExistsAssertion;
        this.profileAssociationRepository = profileAssociationRepository;
    }

    public ProfileAssociation run(UUID id, RejectProfileAssociationRequest request) {
        profileAssociationExistsAssertion.run(id);

        ProfileAssociation profileAssociation = profileAssociationRepository.findOne(id);
        profileAssociation.setStatus(REJECTED);
        profileAssociation.setDescription(request.getDescription());

        return profileAssociationRepository.save(profileAssociation);
    }
}
