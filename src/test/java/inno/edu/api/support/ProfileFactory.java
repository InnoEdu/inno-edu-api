package inno.edu.api.support;

import inno.edu.api.domain.profile.commands.dtos.ApproveProfileAssociationRequest;
import inno.edu.api.domain.profile.experience.commands.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.ProfileAssociationRequest;
import inno.edu.api.domain.profile.commands.dtos.RejectProfileAssociationRequest;
import inno.edu.api.domain.profile.experience.commands.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.profile.experience.models.ExperienceType.PROFESSIONAL;
import static inno.edu.api.domain.profile.association.models.RequestStatus.APPROVED;
import static inno.edu.api.domain.profile.association.models.RequestStatus.PENDING;
import static inno.edu.api.support.SchoolFactory.berkeley;
import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.gustavo;
import static inno.edu.api.support.UserFactory.tuany;
import static java.time.LocalDate.of;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class ProfileFactory {
    public static Profile newAlanProfile(UUID id) {
        return alanProfile().toBuilder()
                .id(id)
                .build();
    }

    public static Profile gustavoProfile() {
        return Profile.builder().id(fromString("e1b66612-a94a-4db3-86a1-04f3a102227b"))
                .userId(gustavo().getId())
                .schoolId(stanford().getId())
                .rate(new BigDecimal(20.5))
                .description("Gustavo is a great mentor.")
                .build();
    }

    public static Profile feiProfile() {
        return Profile.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .userId(fei().getId())
                .schoolId(stanford().getId())
                .description("Fei is a great mentor.")
                .rate(new BigDecimal(10.5))
                .build();
    }

    public static Profile alanProfile() {
        return Profile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .userId(alan().getId())
                .description("Alan is a great mentee.")
                .build();
    }

    public static Profile tuanyProfile() {
        return Profile.builder().id(fromString("c96ae800-fd93-48c2-bbf3-91a6658c8079"))
                .userId(tuany().getId())
                .description("Tuany is a great mentee.")
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

    public static Experience feiExperience() {
        return Experience.builder()
                .id(fromString("7555b5ce-f7a0-4d9a-a902-25f9dcc8de6f"))
                .profileId(feiProfile().getId())
                .title("Owner")
                .area("Area")
                .institution("InnoEdu")
                .location("San Francisco, CA")
                .fromDate(of(2018, 1, 1))
                .toDate(of(2018, 12, 31))
                .description("Great owner.")
                .type(PROFESSIONAL)
                .build();
    }

    public static Experience newFeiExperience(UUID id) {
        return feiExperience().toBuilder()
                .id(id)
                .build();
    }

    public static CreateExperienceRequest createFeiExperienceRequest() {
        return CreateExperienceRequest.builder()
                .title("Owner")
                .area("Area")
                .institution("InnoEdu")
                .location("San Francisco, CA")
                .fromDate(of(2018, 1, 1))
                .toDate(of(2018, 12, 31))
                .description("Great owner.")
                .type(PROFESSIONAL)
                .build();
    }

    public static Experience updatedFeiExperience() {
        return feiExperience().toBuilder()
                .title("Updated Owner")
                .area("Updated Area")
                .institution("Updated InnoEdu")
                .location("Updated San Francisco, CA")
                .fromDate(of(2019, 1, 1))
                .toDate(of(2020, 12, 31))
                .description("Updated Great owner.")
                .build();
    }

    public static UpdateExperienceRequest updateFeiExperienceRequest() {
        return UpdateExperienceRequest.builder()
                .title("Updated Owner")
                .area("Updated Area")
                .institution("Updated InnoEdu")
                .location("Updated San Francisco, CA")
                .fromDate(of(2019, 1, 1))
                .toDate(of(2020, 12, 31))
                .description("Updated Great owner.")
                .build();
    }

    public static List<Experience> feiExperiences() {
        return singletonList(feiExperience());
    }
}
