package inno.edu.api.support;

import inno.edu.api.domain.profile.accomplishment.commands.dtos.CreateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.commands.dtos.UpdateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.association.models.ProfileAssociation;
import inno.edu.api.domain.profile.attachment.commands.dtos.UploadProfileAttachmentRequest;
import inno.edu.api.domain.profile.attachment.models.ProfileAttachment;
import inno.edu.api.domain.profile.experience.commands.dtos.CreateExperienceRequest;
import inno.edu.api.domain.profile.experience.commands.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.interest.commands.dtos.CreateInterestRequest;
import inno.edu.api.domain.profile.interest.commands.dtos.UpdateInterestRequest;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.root.commands.dtos.ApproveProfileAssociationRequest;
import inno.edu.api.domain.profile.root.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.root.commands.dtos.ProfileAssociationRequest;
import inno.edu.api.domain.profile.root.commands.dtos.RejectProfileAssociationRequest;
import inno.edu.api.domain.profile.root.commands.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.service.commands.dtos.CreateServiceRequest;
import inno.edu.api.domain.profile.service.commands.dtos.UpdateServiceRequest;
import inno.edu.api.domain.profile.service.models.Service;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.profile.accomplishment.models.AccomplishmentType.PUBLICATION;
import static inno.edu.api.domain.profile.association.models.RequestStatus.APPROVED;
import static inno.edu.api.domain.profile.association.models.RequestStatus.PENDING;
import static inno.edu.api.domain.profile.experience.models.ExperienceType.PROFESSIONAL;
import static inno.edu.api.infrastructure.configuration.StaticConstants.NO_FILE;
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
                .location("San Francisco, CA")
                .company("Company")
                .profileReferenceId(feiProfile().getId())
                .build();
    }

    public static Profile feiProfile() {
        return Profile.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .userId(fei().getId())
                .schoolId(stanford().getId())
                .description("Fei is a great mentor.")
                .rate(new BigDecimal(10.5))
                .location("San Francisco, CA")
                .build();
    }

    public static Profile alanProfile() {
        return Profile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .userId(alan().getId())
                .description("Alan is a great mentee.")
                .location("San Francisco, CA")
                .build();
    }

    public static Profile tuanyProfile() {
        return Profile.builder().id(fromString("c96ae800-fd93-48c2-bbf3-91a6658c8079"))
                .userId(tuany().getId())
                .description("Tuany is a great mentee.")
                .rate(BigDecimal.ONE)
                .schoolId(stanford().getId())
                .location("San Francisco, CA")
                .company("Company")
                .profileReferenceId(feiProfile().getId())
                .build();
    }

    public static Profile updatedAlanProfile() {
        return alanProfile().toBuilder()
                .description("Updated description")
                .location("Updated San Francisco, CA")
                .profileReferenceId(gustavoProfile().getId())
                .build();
    }

    public static CreateProfileRequest createGustavoProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(gustavo().getId())
                .description(gustavoProfile().getDescription())
                .location(gustavoProfile().getLocation())
                .company(gustavoProfile().getCompany())
                .profileReferenceId(gustavoProfile().getProfileReferenceId())
                .build();
    }

    public static CreateProfileRequest createTuanyProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(tuanyProfile().getUserId())
                .description(tuanyProfile().getDescription())
                .location(tuanyProfile().getLocation())
                .company(tuanyProfile().getCompany())
                .profileReferenceId(tuanyProfile().getProfileReferenceId())
                .build();
    }

    public static List<Profile> profiles() {
        return singletonList(alanProfile());
    }

    public static CreateProfileRequest createAlanProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(alan().getId())
                .description(alanProfile().getDescription())
                .location(alanProfile().getLocation())
                .build();
    }

    public static UpdateProfileRequest updateAlanProfileRequest() {
        return UpdateProfileRequest.builder()
                .description(updatedAlanProfile().getDescription())
                .location(updatedAlanProfile().getLocation())
                .profileReferenceId(updatedAlanProfile().getProfileReferenceId())
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

    public static Interest feiInterest() {
        return Interest.builder()
                .id(fromString("f6c17afc-39d2-475b-827a-3f473db678af"))
                .profileId(feiProfile().getId())
                .title("My interest")
                .description("Perfect interest.")
                .build();
    }

    public static Interest newFeiInterest(UUID id) {
        return feiInterest().toBuilder()
                .id(id)
                .build();
    }

    public static CreateInterestRequest createFeiInterestRequest() {
        return CreateInterestRequest.builder()
                .title(feiInterest().getTitle())
                .description(feiInterest().getDescription())
                .build();
    }

    public static Interest updatedFeiInterest() {
        return feiInterest().toBuilder()
                .title("Updated Title")
                .description("Updated description")
                .build();
    }

    public static UpdateInterestRequest updateFeiInterestRequest() {
        return UpdateInterestRequest.builder()
                .title(updatedFeiInterest().getTitle())
                .description(updatedFeiInterest().getDescription())
                .build();
    }

    public static List<Interest> feiInterests() {
        return singletonList(feiInterest());
    }

    public static Accomplishment feiAccomplishment() {
        return Accomplishment.builder()
                .id(fromString("27f8f745-07e2-4fb8-96d4-5c6623c5cbc6"))
                .profileId(feiProfile().getId())
                .title("My accomplishment")
                .description("Perfect accomplishment.")
                .type(PUBLICATION)
                .build();
    }

    public static Accomplishment newFeiAccomplishment(UUID id) {
        return feiAccomplishment().toBuilder()
                .id(id)
                .build();
    }

    public static CreateAccomplishmentRequest createFeiAccomplishmentRequest() {
        return CreateAccomplishmentRequest.builder()
                .title(feiAccomplishment().getTitle())
                .description(feiAccomplishment().getDescription())
                .type(feiAccomplishment().getType())
                .build();
    }

    public static Accomplishment updatedFeiAccomplishment() {
        return feiAccomplishment().toBuilder()
                .title("Updated Title")
                .description("Updated description")
                .build();
    }

    public static UpdateAccomplishmentRequest updateFeiAccomplishmentRequest() {
        return UpdateAccomplishmentRequest.builder()
                .title(updatedFeiAccomplishment().getTitle())
                .description(updatedFeiAccomplishment().getDescription())
                .build();
    }

    public static List<Accomplishment> feiAccomplishments() {
        return singletonList(feiAccomplishment());
    }

    public static Service feiService() {
        return Service.builder()
                .id(fromString("a9e747ab-1cb9-4941-9608-5f6115bb48ce"))
                .profileId(feiProfile().getId())
                .title("My service")
                .description("Perfect service.")
                .build();
    }

    public static Service newFeiService(UUID id) {
        return feiService().toBuilder()
                .id(id)
                .build();
    }

    public static CreateServiceRequest createFeiServiceRequest() {
        return CreateServiceRequest.builder()
                .title(feiService().getTitle())
                .description(feiService().getDescription())
                .build();
    }

    public static Service updatedFeiService() {
        return feiService().toBuilder()
                .title("Updated Title")
                .description("Updated description")
                .build();
    }

    public static UpdateServiceRequest updateFeiServiceRequest() {
        return UpdateServiceRequest.builder()
                .title(updatedFeiService().getTitle())
                .description(updatedFeiService().getDescription())
                .build();
    }

    public static List<Service> feiServices() {
        return singletonList(feiService());
    }

    public static ProfileAttachment feiProfileAttachment() {
            return ProfileAttachment.builder()
                    .id(fromString("0a2de2db-3997-4082-82b6-56aad9746af9"))
                    .description("My File.")
                    .profileId(feiProfile().getId())
                    .link(NO_FILE)
                    .build();
    }

    public static UploadProfileAttachmentRequest feiUploadAttachmentRequest() {
        return UploadProfileAttachmentRequest.builder()
                .profileId(feiProfileAttachment().getProfileId())
                .description(feiProfileAttachment().getDescription())
                .file(new MockMultipartFile("file", feiProfileAttachment().getLink(), null, "bar".getBytes()))
                .build();
    }
}
