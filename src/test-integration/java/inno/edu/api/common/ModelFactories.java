package inno.edu.api.common;

import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.user.models.User;

import static java.util.UUID.fromString;

public class ModelFactories {
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

    public static University university() {
        return University.builder().id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .name("FirstName").build();
    }

    public static University otherUniversity() {
        return University.builder().id(fromString("8d6153fc-83e5-4b3a-90ac-d081ff789cef"))
                .name("OtherFirstName").build();
    }

    public static University updatedUniversity() {
        return University.builder().id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .name("FirstNameUpdated").build();
    }

    public static String universityPayload() {
        return "{\"name\": \"%s\" }";
    }

}
