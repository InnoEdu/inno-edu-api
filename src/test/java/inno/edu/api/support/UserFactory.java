package inno.edu.api.support;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.user.models.Login;
import inno.edu.api.domain.user.models.User;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static inno.edu.api.support.SchoolFactory.stanford;
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

    public static MentorProfile gustavoProfile() {
        return MentorProfile.builder().id(fromString("e1b66612-a94a-4db3-86a1-04f3a102227b"))
                .mentorId(gustavo().getId())
                .schoolId(stanford().getId())
                .email("gustavo@inno.edu")
                .description("Gustavo is a great mentee.")
                .status(CREATED)
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

    public static List<User> users() {
        return newArrayList(fei(), alan());
    }
}
