package inno.edu.api.support;

import inno.edu.api.domain.user.models.Login;
import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.models.User;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.domain.user.models.ProfileStatus.ACTIVE;
import static inno.edu.api.support.SchoolFactory.stanford;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class UserFactory {
    public static User gustavo() {
        return User.builder()
                .id(fromString("df54ff86-3caa-4145-b228-284f5d4a908a"))
                .firstName("Gustavo")
                .lastName("Domenico")
                .userName("gustavodomenico")
                .password("password")
                .isMentor(true)
                .build();
    }

    public static User fei() {
        return User.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("Fei")
                .lastName("Xiu")
                .userName("feixiu")
                .password("password")
                .photoUrl("https://i1.rgstatic.net/ii/profile.image/AS%3A278674336174081%401443452547142_xl/Peng_Fei_Xu.png")
                .isMentor(true)
                .build();
    }

    public static Login feiCredentials() {
        return Login.builder()
                .userName(fei().getUserName())
                .password(fei().getPassword())
                .build();
    }


    public static User alan() {
        return User.builder().id(fromString("8d6153fc-83e5-4b3a-90ac-d081ff789cef"))
                .firstName("Alan")
                .lastName("Ly")
                .userName("alanly")
                .password("password")
                .photoUrl("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAeBAAAAJDk1ZDQyNzE0LWY2MGQtNDFmYS05MmRmLTRhMzc0MTAxMGEwMQ.jpg")
                .isMentor(false)
                .build();
    }

    public static MenteeProfile gustavoProfile() {
        return MenteeProfile.builder().id(fromString("4fbee407-5ab3-4d4b-9ef0-39840e939d5e"))
                .menteeId(fei().getId())
                .email("gustavo@inno.edu")
                .description("Gustavo is a great mentee.")
                .build();
    }

    public static MenteeProfile alanProfile() {
        return MenteeProfile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .menteeId(alan().getId())
                .email("alanly@inno.edu")
                .description("Alan is a great mentee.")
                .build();
    }

    public static MentorProfile feiProfile() {
        return MentorProfile.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .mentorId(fei().getId())
                .schoolId(stanford().getId())
                .email("feixiu@inno.edu")
                .description("Fei is a great mentor.")
                .status(ACTIVE)
                .build();
    }

    public static User updatedFei() {
        return User.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("UpdatedFei")
                .lastName("UpdatedXiu")
                .userName("feixiu")
                .photoUrl("UpdatedProfileUrl")
                .isMentor(true)
                .build();
    }

    public static MenteeProfile updatedAlanProfile() {
        return MenteeProfile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .menteeId(alan().getId())
                .email("Updatedalanly@inno.edu")
                .description("Updated description")
                .build();
    }

    public static MentorProfile updatedFeiProfile() {
        return MentorProfile.builder().id(fromString("0e9e40c0-b44b-4387-92a9-9d75d10e3d42"))
                .mentorId(fei().getId())
                .schoolId(stanford().getId())
                .email("Updatedfeixiu@inno.edu")
                .description("Updated description")
                .status(ACTIVE)
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
