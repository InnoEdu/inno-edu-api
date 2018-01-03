package inno.edu.api.support;

import inno.edu.api.domain.profile.commands.dtos.CreateProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateProfileRequest;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.dtos.MentorProfileUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;
import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.gustavo;
import static inno.edu.api.support.UserFactory.tuany;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class ProfileFactory {
    // New

    public static Profile newNewAlanProfile(UUID id, ProfileStatus status) {
        return newAlanProfile().toBuilder()
                .id(id)
                .status(status)
                .build();
    }

    public static Profile newGustavoProfile() {
        return Profile.builder().id(fromString("e1b66612-a94a-4db3-86a1-04f3a102227b"))
                .userId(gustavo().getId())
                .schoolId(stanford().getId())
                .rate(new BigDecimal(20.5))
                .description("Gustavo is a great mentor.")
                .status(CREATED)
                .build();
    }


    public static Profile newAlanProfile() {
        return Profile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .userId(alan().getId())
                .description("Alan is a great mentee.")
                .status(CREATED)
                .build();
    }

    public static Profile newTuanyProfile() {
        return Profile.builder().id(fromString("c96ae800-fd93-48c2-bbf3-91a6658c8079"))
                .userId(tuany().getId())
                .description("Tuany is a great mentee.")
                .status(CREATED)
                .rate(BigDecimal.ONE)
                .schoolId(stanford().getId())
                .build();
    }

    public static Profile updatedNewAlanProfile() {
        return newAlanProfile().toBuilder()
                .description("Updated description")
                .build();
    }

    public static CreateProfileRequest createNewGustavoProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(gustavo().getId())
                .description("Gustavo is a great mentor.")
                .build();
    }

    public static CreateProfileRequest createNewTuanyProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(newTuanyProfile().getUserId())
                .description(newTuanyProfile().getDescription())
                .build();
    }

    public static List<Profile> profiles() {
        return singletonList(newAlanProfile());
    }

    public static CreateProfileRequest createNewAlanProfileRequest() {
        return CreateProfileRequest.builder()
                .userId(alan().getId())
                .description("Alan is a great mentee.")
                .build();
    }

    public static UpdateProfileRequest updateNewAlanProfileRequest() {
        return UpdateProfileRequest.builder()
                .description(updatedNewAlanProfile().getDescription())
                .build();
    }


    ///

    public static MentorProfile gustavoProfile() {
        return MentorProfile.builder().id(fromString("e1b66612-a94a-4db3-86a1-04f3a102227b"))
                .mentorId(gustavo().getId())
                .schoolId(stanford().getId())
                .email("gustavo@inno.edu")
                .description("Gustavo is a great mentee.")
                .rate(new BigDecimal(5))
                .status(CREATED)
                .build();
    }

    public static MentorProfile feiProfile() {
        return MentorProfile.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .mentorId(fei().getId())
                .schoolId(stanford().getId())
                .email("feixiu@inno.edu")
                .description("Fei is a great mentor.")
                .status(ACTIVE)
                .rate(new BigDecimal(10.5))
                .build();
    }

    public static MentorProfile newFeiProfile(UUID id, ProfileStatus status) {
        return feiProfile().toBuilder()
                .id(id)
                .status(status)
                .build();
    }

    public static MentorProfileUser feiProfileUser() {
        return MentorProfileUser.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .mentorId(fei().getId())
                .schoolId(stanford().getId())
                .email("feixiu@inno.edu")
                .description("Fei is a great mentor.")
                .firstName(fei().getFirstName())
                .lastName(fei().getLastName())
                .build();
    }

    public static MentorProfile updatedFeiProfile() {
        return feiProfile().toBuilder()
                .description("Updated description")
                .rate(new BigDecimal(20.5))
                .build();
    }

    public static List<MentorProfile> mentorProfiles() {
        return singletonList(feiProfile());
    }

    public static List<MentorProfileUser> mentorProfileUsers() {
        return singletonList(feiProfileUser());
    }

}
