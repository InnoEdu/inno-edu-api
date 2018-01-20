package inno.edu.api.support;

import inno.edu.api.domain.user.models.dtos.CreateUserRequest;
import inno.edu.api.domain.user.models.dtos.LoginRequest;
import inno.edu.api.domain.user.models.dtos.LoginResponse;
import inno.edu.api.domain.user.models.dtos.UpdateUserRequest;
import inno.edu.api.domain.user.models.ApplicationUser;

import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.infrastructure.security.SecurityConstants.TOKEN_PREFIX;
import static java.util.UUID.fromString;

public class UserFactory {
    public static ApplicationUser gustavo() {
        return ApplicationUser.builder()
                .id(fromString("df54ff86-3caa-4145-b228-284f5d4a908a"))
                .firstName("Gustavo")
                .lastName("Domenico")
                .username("gustavodomenico")
                .email("gustavo@inno.edu")
                .password("password")
                .build();
    }

    public static ApplicationUser fei() {
        return ApplicationUser.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("Fei")
                .lastName("Xiu")
                .username("feixiu")
                .email("feixiu@inno.edu")
                .password("password")
                .build();
    }

    public static ApplicationUser userToDelete() {
        return fei().toBuilder()
                .id(fromString("e79b6b2e-cc08-4e37-b227-51cd64f5aa2e"))
                .build();
    }

    public static ApplicationUser newFei(UUID id) {
        return fei().toBuilder()
                .id(id)
                .build();
    }

    public static UpdateUserRequest updateFeiRequest() {
        return UpdateUserRequest.builder()
                .firstName(updatedFei().getFirstName())
                .lastName(updatedFei().getLastName())
                .email(updatedFei().getEmail())
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
                .build();
    }

    public static LoginRequest feiCredentials() {
        return LoginRequest.builder()
                .username(fei().getUsername())
                .password(fei().getPassword())
                .build();
    }

    public static LoginResponse feiLoginResponse() {
        return LoginResponse.builder()
                .user(fei())
                .token(TOKEN_PREFIX + "TOKEN")
                .build();
    }

    public static ApplicationUser alan() {
        return ApplicationUser.builder().id(fromString("8d6153fc-83e5-4b3a-90ac-d081ff789cef"))
                .firstName("Alan")
                .lastName("Ly")
                .username("alanly")
                .email("alanly@inno.edu")
                .password("password")
                .build();
    }

    public static ApplicationUser tuany() {
        return ApplicationUser.builder()
                .id(fromString("1e4cc51c-767f-4524-b4e0-4f2e4bd249da"))
                .build();
    }

    public static ApplicationUser updatedFei() {
        return fei().toBuilder()
                .firstName("UpdatedFei")
                .lastName("UpdatedXiu")
                .email("UpdatedEmail@email.com")
                .build();
    }

    public static List<ApplicationUser> users() {
        return newArrayList(fei(), alan());
    }
}
