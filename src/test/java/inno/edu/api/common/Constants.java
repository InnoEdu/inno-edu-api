package inno.edu.api.common;

import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.models.User;
import org.springframework.hateoas.Resources;

import java.util.List;

import static java.util.Collections.singletonList;

public class Constants {
    public static User user() {
        return User.builder().id(10000).build();
    }

    private static UserResource userResource() {
        return new UserResource(user());
    }

    public static List<User> users() {
        return singletonList(user());
    }

    public static Resources<UserResource> usersResource() {
        return new Resources<>(singletonList(userResource()));
    }

}
