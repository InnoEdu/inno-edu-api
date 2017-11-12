package inno.edu.api.factories;

import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.models.User;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.factories.UniversityFactory.stanford;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class UserFactory {
    public static User gustavo() {
        return User.builder()
                .id(fromString("b654e244-c423-4395-ba34-a6ce1afb69ff"))
                .firstName("Gustavo")
                .lastName("Domenico")
                .userName("gustavodomenico")
                .isMentor(true)
                .build();
    }

    public static User fei() {
        return User.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("Fei")
                .lastName("Xiu")
                .userName("feixiu")
                .isMentor(true)
                .build();
    }

    public static User alan() {
        return User.builder().id(fromString("8d6153fc-83e5-4b3a-90ac-d081ff789cef"))
                .firstName("Alan")
                .lastName("Ly")
                .userName("alanly")
                .isMentor(false)
                .build();
    }

    public static MenteeProfile gustavoProfile() {
        return MenteeProfile.builder().id(fromString("4fbee407-5ab3-4d4b-9ef0-39840e939d5e"))
                .menteeId(fei().getId())
                .email("gustavo@inno.edu")
                .build();
    }

    public static MenteeProfile alanProfile() {
        return MenteeProfile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .menteeId(alan().getId())
                .email("alanly@inno.edu")
                .build();
    }

    public static MentorProfile feiProfile() {
        return MentorProfile.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .mentorId(fei().getId())
                .universityId(stanford().getId())
                .email("feixiu@inno.edu")
                .isActive(true)
                .build();
    }

    public static User updatedFei() {
        return User.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("UpdatedFei")
                .lastName("UpdatedXiu")
                .userName("Updatedfeixiu")
                .isMentor(true)
                .build();
    }

    public static MenteeProfile updatedAlanProfile() {
        return MenteeProfile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .menteeId(alan().getId())
                .email("Updatedalanly@inno.edu")
                .build();
    }

    public static MentorProfile updatedFeiProfile() {
        return MentorProfile.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .mentorId(fei().getId())
                .universityId(stanford().getId())
                .email("Updatedfeixiu@inno.edu")
                .isActive(true)
                .build();
    }

    public static List<MenteeProfile> menteeProfiles() {
        return singletonList(alanProfile());
    }

    public static List<MentorProfile> mentorProfiles() {
        return singletonList(feiProfile());
    }

    public static List<User> users() {
        return newArrayList(fei(), alan());
    }
}
