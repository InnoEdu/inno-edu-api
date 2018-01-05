package inno.edu.api.domain.profile.association.commands;

import inno.edu.api.domain.profile.association.assertions.ProfileAssociationExistsAssertion;
import inno.edu.api.domain.profile.commands.dtos.ApproveProfileAssociationRequest;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

import static inno.edu.api.domain.profile.association.models.RequestStatus.APPROVED;

@Command
public class ApproveProfileAssociationCommand {
    private final ProfileAssociationExistsAssertion profileAssociationExistsAssertion;

    private final ProfileAssociationRepository profileAssociationRepository;
    private final ProfileRepository profileRepository;

    public ApproveProfileAssociationCommand(ProfileAssociationExistsAssertion profileAssociationExistsAssertion, ProfileAssociationRepository profileAssociationRepository, ProfileRepository profileRepository) {
        this.profileAssociationExistsAssertion = profileAssociationExistsAssertion;
        this.profileAssociationRepository = profileAssociationRepository;
        this.profileRepository = profileRepository;
    }

    public ProfileAssociation run(UUID id, ApproveProfileAssociationRequest request) {
        profileAssociationExistsAssertion.run(id);

        ProfileAssociation profileAssociation = profileAssociationRepository.findOne(id);

        Profile profile = profileRepository.findOne(profileAssociation.getProfileId());
        profile.setSchoolId(profileAssociation.getSchoolId());
        profile.setRate(request.getRate());
        profileRepository.save(profile);

        profileAssociation.setStatus(APPROVED);
        return profileAssociationRepository.save(profileAssociation);
    }
}
