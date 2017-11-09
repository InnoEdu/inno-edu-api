package inno.edu.api.factories;

import inno.edu.api.domain.user.models.User;

import static java.util.UUID.fromString;

public class UserFactory {
    public static User user() {
        return User.builder().id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("FirstName")
                .lastName("LastName").build();
    }

    public static User otherUser() {
        return User.builder().id(fromString("8d6153fc-83e5-4b3a-90ac-d081ff789cef"))
                .firstName("OtherFirstName")
                .lastName("OtherLastName").build();
    }

    public static User updatedUser() {
        return User.builder().id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("FirstNameUpdated")
                .lastName("LastNameUpdated").build();
    }

    public static String userPayload() {

        return "{\"firstName\": \"%s\", \"lastName\": \"%s\"}";
    }
}
