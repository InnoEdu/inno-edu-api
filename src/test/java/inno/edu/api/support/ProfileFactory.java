package inno.edu.api.support;

import inno.edu.api.domain.profile.commands.dtos.CreateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateMentorProfileRequest;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.models.MentorProfile;
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
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class ProfileFactory {
    public static MentorProfile gustavoProfile() {
        return MentorProfile.builder().id(fromString("e1b66612-a94a-4db3-86a1-04f3a102227b"))
                .mentorId(gustavo().getId())
                .schoolId(stanford().getId())
                .email("gustavo@inno.edu")
                .description("Gustavo is a great mentee.")
                .status(CREATED)
                .build();
    }

    public static MenteeProfile alanProfile() {
        return MenteeProfile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .menteeId(alan().getId())
                .description("Alan is a great mentee.")
                .build();
    }

    public static MenteeProfile newAlanProfile(UUID id) {
        return alanProfile().toBuilder()
                .id(id)
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

    public static MenteeProfile tuanyProfile() {
        return MenteeProfile.builder().id(fromString("4fbee407-5ab3-4d4b-9ef0-39840e939d5e"))
                .menteeId(gustavo().getId())
                .description("Tuany is a great mentee.")
                .build();
    }

    public static CreateMentorProfileRequest createFeiProfileRequest() {
        return CreateMentorProfileRequest.builder()
                .mentorId(fei().getId())
                .schoolId(stanford().getId())
                .email("feixiu@inno.edu")
                .rate(new BigDecimal(10.5))
                .description("Fei is a great mentor.")
                .build();
    }

    public static CreateMenteeProfileRequest createTuanyProfileRequest() {
        return CreateMenteeProfileRequest.builder()
                .menteeId(gustavo().getId())
                .description("Tuany is a great mentee.")
                .build();
    }

    public static CreateMenteeProfileRequest createAlanProfileRequest() {
        return CreateMenteeProfileRequest.builder()
                .menteeId(alan().getId())
                .description("Alan is a great mentee.")
                .build();
    }

    public static MenteeProfile updatedAlanProfile() {
        return alanProfile().toBuilder()
                .description("Updated description")
                .build();
    }
    public static UpdateMenteeProfileRequest updateAlanProfileRequest() {
        return UpdateMenteeProfileRequest.builder()
                .description(updatedAlanProfile().getDescription())
                .build();
    }

    public static MentorProfile updatedFeiProfile() {
        return feiProfile().toBuilder()
                .description("Updated description")
                .rate(new BigDecimal(20.5))
                .build();
    }

    public static UpdateMentorProfileRequest updateFeiProfileRequest() {
        return UpdateMentorProfileRequest.builder()
                .description(updatedFeiProfile().getDescription())
                .rate(updatedFeiProfile().getRate())
                .build();
    }

    public static List<MenteeProfile> menteeProfiles() {
        return singletonList(alanProfile());
    }

    public static List<MentorProfile> mentorProfiles() {
        return singletonList(feiProfile());
    }

    public static List<MentorProfileUser> mentorProfileUsers() {
        return singletonList(feiProfileUser());
    }

}
