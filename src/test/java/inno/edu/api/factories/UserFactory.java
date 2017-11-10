package inno.edu.api.factories;

import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.models.User;
import org.springframework.hateoas.Resources;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.UUID.fromString;

public class UserFactory {
    public static User fei() {
        return User.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("Fei")
                .lastName("Xiu")
                .build();
    }

    public static User alan() {
        return User.builder().id(fromString("8d6153fc-83e5-4b3a-90ac-d081ff789cef"))
                .firstName("Alan")
                .lastName("LastName")
                .build();
    }

    public static User updatedFei() {
        return User.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("UpdatedFei")
                .lastName("UpdatedXiu")
                .build();
    }

    private static UserResource feiResource() {
        return new UserResource(fei());
    }

    private static UserResource alanResource() {
        return new UserResource(alan());
    }

    public static List<User> users() {
        return newArrayList(fei(), alan());
    }

    public static Resources<UserResource> usersResource() {
        return new Resources<>(newArrayList(feiResource(), alanResource()));
    }

    public static String userPayload() {
        return "{\"firstName\": \"%s\", \"lastName\": \"%s\"}";
    }
}
