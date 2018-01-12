package inno.edu.api.domain.profile.association.commands;

import inno.edu.api.domain.profile.association.assertions.CheckPendingAssociationsAssertion;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.root.commands.dtos.ProfileAssociationRequest;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.association.repositories.ProfileAssociationRepository;
import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

import static inno.edu.api.domain.profile.association.models.RequestStatus.PENDING;

@Command
public class AssociateProfileCommand {
    private final ProfileExistsAssertion profileExistsAssertion;
    private final SchoolExistsAssertion schoolExistsAssertion;
    private final CheckPendingAssociationsAssertion checkPendingAssociationsAssertion;

    private final ProfileAssociationRepository profileAssociationRepository;
    private final UUIDGeneratorService uuidGeneratorService;

    public AssociateProfileCommand(ProfileExistsAssertion profileExistsAssertion, SchoolExistsAssertion schoolExistsAssertion, CheckPendingAssociationsAssertion checkPendingAssociationsAssertion, ProfileAssociationRepository profileAssociationRepository, UUIDGeneratorService uuidGeneratorService) {
        this.profileExistsAssertion = profileExistsAssertion;
        this.schoolExistsAssertion = schoolExistsAssertion;
        this.checkPendingAssociationsAssertion = checkPendingAssociationsAssertion;
        this.profileAssociationRepository = profileAssociationRepository;
        this.uuidGeneratorService = uuidGeneratorService;
    }

    public ProfileAssociation run(UUID id, ProfileAssociationRequest profileAssociationRequest) {
        profileExistsAssertion.run(id);
        checkPendingAssociationsAssertion.run(id);
        schoolExistsAssertion.run(profileAssociationRequest.getSchoolId());

        ProfileAssociation profileAssociation = ProfileAssociation.builder()
                .id(uuidGeneratorService.generate())
                .profileId(id)
                .schoolId(profileAssociationRequest.getSchoolId())
                .status(PENDING)
                .build();

        return profileAssociationRepository.save(profileAssociation);
    }
}
