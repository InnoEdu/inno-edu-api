package inno.edu.api.factories;

import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.models.User;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class UserFactory {
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

    public static MenteeProfile alanProfile() {
        return MenteeProfile.builder().id(fromString("c5f473b4-3311-40b1-8fb3-f70357894754"))
                .menteeId(alan().getId())
                .email("alanly@inno.edu")
                .build();
    }

    public static User updatedFei() {
        return User.builder()
                .id(fromString("ba7c6505-19fd-47c3-87a6-c6af6e5322b7"))
                .firstName("UpdatedFei")
                .lastName("UpdatedXiu")
                .userName("Updatedfeixiu")
                .isMentor(false)
                .build();
    }

    public static List<MenteeProfile> menteeProfiles() {
        return singletonList(alanProfile());
    }

    public static List<User> users() {
        return newArrayList(fei(), alan());
    }

    public static String userPayload(User user) {
        return format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"userName\": \"%s\", \"isMentor\": \"%s\"}", user.getFirstName(), user.getLastName(), user.getUserName(), user.getIsMentor());
    }
}
