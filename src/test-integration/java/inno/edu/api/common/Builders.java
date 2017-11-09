package inno.edu.api.common;

import inno.edu.api.domain.user.models.User;

import static java.util.UUID.fromString;

public class Builders {
    public static User user() {
        return User.builder().id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("FirstName")
                .lastName("LastName").build();
    }

    public static User otherUser() {
        return User.builder().id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("FirstNameUpdated")
                .lastName("LastNameUpdated").build();
    }

    public static String userPayload() {
        return "{\"firstName\": \"%s\", \"lastName\": \"%s\"}";
    }
}
