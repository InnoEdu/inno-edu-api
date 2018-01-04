package inno.edu.api.support;

import inno.edu.api.domain.profile.commands.dtos.ApproveProfileAssociationRequest;
import inno.edu.api.domain.profile.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.ProfileAssociationRequest;
import inno.edu.api.domain.profile.commands.dtos.RejectProfileAssociationRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.models.ProfileAssociation;
import inno.edu.api.domain.profile.models.ProfileStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;
import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static inno.edu.api.domain.profile.models.RequestStatus.APPROVED;
import static inno.edu.api.domain.profile.models.RequestStatus.PENDING;
import static inno.edu.api.support.SchoolFactory.berkeley;
import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.gustavo;
import static inno.edu.api.support.UserFactory.tuany;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class ProfileFactory {
    public static Profile newAlanProfile(UUID id, ProfileStatus status) {
        return alanProfile().toBuilder()
                .id(id)
                .status(status)
                .build();
    }

    public static Profile gustavoProfile() {
        return Profile.builder().id(fromString("e1b66612-a94a-4db3-86a1-04f3a102227b"))
                .userId(gustavo().getId())
                .schoolId(stanford().getId())
                .rate(new BigDecimal(20.5))
                .description("Gustavo is a great mentor.")
                .status(ACTIVE)
                .build();
    }

    public static Profile feiProfile() {
        return Profile.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .userId(fei().getId())
                .schoolId(stanford().getId())
                .description("Fei is a great mentor.")
                .status(ACTIVE)
                .rate(new BigDecimal(10.5))
                .build();
    }

    public static Profile alanProfile() {
        return Profile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .userId(alan().getId())
                .description("Alan is a great mentee.")
                .status(CREATED)
                .build();
    }

    public static Profile tuanyProfile() {
        return Profile.builder().id(fromString("c96ae800-fd93-48c2-bbf3-91a6658c8079"))
                .userId(tuany().getId())
                .description("Tuany is a great mentee.")
                .status(CREATED)
                .rate(BigDecimal.ONE)
                .schoolId(stanford().getId())
                .build();
    }

    public static Profile updatedAlanProfile() {
        return alanProfile().toBuilder()
                .description("Updated description")
                .build();
    }

    public static CreateProfileRequest createGustavoProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(gustavo().getId())
                .description("Gustavo is a great mentor.")
                .build();
    }

    public static CreateProfileRequest createTuanyProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(tuanyProfile().getUserId())
                .description(tuanyProfile().getDescription())
                .build();
    }

    public static List<Profile> profiles() {
        return singletonList(alanProfile());
    }

    public static CreateProfileRequest createAlanProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(alan().getId())
                .description("Alan is a great mentee.")
                .build();
    }

    public static UpdateProfileRequest updateAlanProfileRequest() {
        return UpdateProfileRequest.builder()
                .description(updatedAlanProfile().getDescription())
                .build();
    }

    public static ProfileAssociationRequest gustavoToBerkeleyRequest() {
        return ProfileAssociationRequest.builder()
                .schoolId(berkeley().getId())
                .build();
    }

    public static ProfileAssociation gustavoProfileAssociation() {
        return ProfileAssociation.builder()
                .id(fromString("9db7c055-b02e-4fbe-9e0f-57dbb027767e"))
                .schoolId(gustavoToBerkeleyRequest().getSchoolId())
                .profileId(gustavoProfile().getId())
                .status(PENDING)
                .build();
    }

    public static ProfileAssociation feiProfileAssociation() {
        return ProfileAssociation.builder()
                .id(fromString("a29aab94-7004-4ac6-9081-ebea3890df51"))
                .schoolId(stanford().getId())
                .profileId(feiProfile().getId())
                .status(APPROVED)
                .description("Approved")
                .build();
    }

    public static ApproveProfileAssociationRequest approveRequest() {
        return ApproveProfileAssociationRequest.builder().rate(BigDecimal.TEN).build();
    }

    public static RejectProfileAssociationRequest rejectRequest() {
        return RejectProfileAssociationRequest.builder().description("Rejected").build();
    }

    public static List<ProfileAssociation> associations() {
        return singletonList(gustavoProfileAssociation());
    }
}
