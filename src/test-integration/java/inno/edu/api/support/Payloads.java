package inno.edu.api.support;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.school.models.School;
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
        return format(loadPayload("payloads/user/post-user.json"), user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(), user.getIsMentor(), user.getPhotoUrl());
    }

    public static String putUserPayload(User user) {
        return format(loadPayload("payloads/user/put-user.json"), user.getFirstName(), user.getLastName(), user.getPhotoUrl());
    }

    public static String postMentorProfilePayload(MentorProfile profile) {
        return format(loadPayload("payloads/user/post-mentor-profile.json"), profile.getMentorId(), profile.getSchoolId(), profile.getEmail(), profile.getDescription());
    }

    public static String putMentorProfilePayload(MentorProfile profile) {
        return format(loadPayload("payloads/user/put-mentor-profile.json"), profile.getEmail(), profile.getDescription(), profile.getStatus());
    }

    public static String postMenteeProfilePayload(MenteeProfile profile) {
        return format(loadPayload("payloads/user/post-mentee-profile.json"), profile.getMenteeId(), profile.getEmail(), profile.getDescription());
    }

    public static String putMenteeProfilePayload(MenteeProfile profile) {
        return format(loadPayload("payloads/user/put-mentee-profile.json"), profile.getEmail(), profile.getDescription());
    }

    public static String postSchoolPayload(School school) {
        return format(loadPayload("payloads/school/post-school.json"), school.getName(), school.getDescription(), school.getPhotoUrl());
    }

    public static String putSchoolPayload(School school) {
        return format(loadPayload("payloads/school/put-school.json"), school.getName(), school.getDescription(), school.getPhotoUrl());
    }

    public static String postAvailabilityPayload(Availability availability) {
        return format(loadPayload("payloads/availability/post-availability.json"), availability.getMentorProfileId(), availability.getFromDateTime(), availability.getToDateTime());
    }

    public static String putAvailabilityPayload(Availability availability) {
        return format(loadPayload("payloads/availability/put-availability.json"), availability.getFromDateTime(), availability.getToDateTime());
    }

    public static String postAppointmentPayload(Appointment appointment) {
        return format(loadPayload("payloads/appointment/post-appointment.json"), appointment.getMentorProfileId(), appointment.getMenteeProfileId(), appointment.getFromDateTime(), appointment.getToDateTime(), appointment.getDescription());
    }

    public static String putAppointmentPayload(Appointment appointment) {
        return format(loadPayload("payloads/appointment/put-appointment.json"), appointment.getFromDateTime(), appointment.getToDateTime(), appointment.getDescription(), appointment.getStatus());
    }
}
