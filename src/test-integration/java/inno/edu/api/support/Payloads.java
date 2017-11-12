package inno.edu.api.support;

import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.models.User;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

import static java.lang.String.format;

public class Payloads {
    private static String loadPayload(String fileName) {
        String result = "";

        ClassLoader classLoader = Payloads.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String postUserPayload(User user) {
        return format(loadPayload("payloads/user/post-user.json"), user.getFirstName(), user.getLastName(), user.getUserName(), user.getIsMentor());
    }

    public static String putUserPayload(User user) {
        return format(loadPayload("payloads/user/put-user.json"), user.getFirstName(), user.getLastName(), user.getUserName());
    }

    public static String postMentorProfilePayload(MentorProfile profile) {
        return format(loadPayload("payloads/user/post-mentor-profile.json"), profile.getMentorId(), profile.getUniversityId(), profile.getEmail());
    }

    public static String putMentorProfilePayload(MentorProfile profile) {
        return format(loadPayload("payloads/user/put-mentor-profile.json"), profile.getEmail(), profile.getIsActive());
    }

    public static String postMenteeProfilePayload(MenteeProfile profile) {
        return format(loadPayload("payloads/user/post-mentee-profile.json"), profile.getMenteeId(), profile.getEmail());
    }

    public static String putMenteeProfilePayload(MenteeProfile profile) {
        return format(loadPayload("payloads/user/put-mentee-profile.json"), profile.getEmail());
    }

    public static String postUniversityPayload(University university) {
        return format(loadPayload("payloads/university/post-university.json"), university.getName());
    }

    public static String putUniversityPayload(University university) {
        return format(loadPayload("payloads/university/put-university.json"), university.getName());
    }

    public static String postAvailabilityPayload(Availability availability) {
        return format(loadPayload("payloads/availability/post-availability.json"), availability.getMentorId(), availability.getUniversityId(), availability.getFromDateTime(), availability.getToDateTime());
    }

    public static String putAvailabilityPayload(Availability availability) {
        return format(loadPayload("payloads/availability/put-availability.json"), availability.getFromDateTime(), availability.getToDateTime());
    }

}
