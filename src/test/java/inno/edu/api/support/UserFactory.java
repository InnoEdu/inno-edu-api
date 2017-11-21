package inno.edu.api.support;

import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import inno.edu.api.domain.user.commands.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.models.User;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.UUID.fromString;

public class UserFactory {
    public static User gustavo() {
        return User.builder()
                .id(fromString("df54ff86-3caa-4145-b228-284f5d4a908a"))
                .firstName("Gustavo")
                .lastName("Domenico")
                .username("gustavodomenico")
                .email("gustavo@inno.edu")
                .password("password")
                .isMentor(true)
                .build();
    }

    public static User fei() {
        return User.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("Fei")
                .lastName("Xiu")
                .username("feixiu")
                .email("feixiu@inno.edu")
                .password("password")
                .photoUrl("https://i1.rgstatic.net/ii/profile.image/AS%3A278674336174081%401443452547142_xl/Peng_Fei_Xu.png")
                .isMentor(true)
                .build();
    }

    public static UpdateUserRequest updateFeiRequest() {
        return UpdateUserRequest.builder()
                .firstName(updatedFei().getFirstName())
                .lastName(updatedFei().getLastName())
                .email(updatedFei().getEmail())
                .photoUrl(updatedFei().getPhotoUrl())
                .build();
    }

    public static CreateUserRequest createFeiRequest() {
        return CreateUserRequest.builder()
                .firstName("Fei")
                .lastName("Xiu")
                .username("feixiu")
                .email("feixiu@inno.edu")
                .password("password")
                .confirmPassword("password")
                .isMentor(true)
                .build();
    }

    public static CreateUserRequest createGustavoRequest() {
        return CreateUserRequest.builder()
                .firstName("Gustavo")
                .lastName("Domenico")
                .username("gustavodomenico")
                .email("gustavo@inno.edu")
                .password("password")
                .confirmPassword("password")
                .isMentor(true)
                .build();
    }

    public static LoginRequest feiCredentials() {
        return LoginRequest.builder()
                .username(fei().getUsername())
                .password(fei().getPassword())
                .build();
    }

    public static User alan() {
        return User.builder().id(fromString("8d6153fc-83e5-4b3a-90ac-d081ff789cef"))
                .firstName("Alan")
                .lastName("Ly")
                .username("alanly")
                .email("alanly@inno.edu")
                .password("password")
                .photoUrl("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAeBAAAAJDk1ZDQyNzE0LWY2MGQtNDFmYS05MmRmLTRhMzc0MTAxMGEwMQ.jpg")
                .isMentor(false)
                .build();
    }

    public static User updatedFei() {
        return fei().toBuilder()
                .firstName("UpdatedFei")
                .lastName("UpdatedXiu")
                .email("UpdatedEmail")
                .photoUrl("UpdatedProfileUrl")
                .build();
    }

    public static List<User> users() {
        return newArrayList(fei(), alan());
    }
}
