package inno.edu.api.support;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentReason;
import inno.edu.api.domain.availability.commands.dtos.CreateAvailabilityRequest;
import inno.edu.api.domain.availability.commands.dtos.UpdateAvailabilityRequest;
import inno.edu.api.domain.profile.commands.dtos.CreateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.CreateMentorProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.dtos.UpdateMentorProfileRequest;
import inno.edu.api.domain.school.commands.dtos.CreateSchoolRequest;
import inno.edu.api.domain.school.commands.dtos.UpdateSchoolRequest;
import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.commands.dtos.LoginRequest;
import inno.edu.api.domain.user.commands.dtos.UpdateUserRequest;
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

    public static String loginUserPayload(LoginRequest credentials) {
        return format(loadPayload("payloads/user/login-user.json"), credentials.getUsername(), credentials.getPassword());
    }

    public static String postUserPayload(CreateUserRequest createUserRequest) {
        return format(loadPayload("payloads/user/post-user.json"), createUserRequest.getFirstName(), createUserRequest.getLastName(), createUserRequest.getUsername(), createUserRequest.getEmail(), createUserRequest.getPassword(), createUserRequest.getConfirmPassword(), createUserRequest.getIsMentor());
    }

    public static String putUserPayload(UpdateUserRequest updateUserRequest) {
        return format(loadPayload("payloads/user/put-user.json"), updateUserRequest.getFirstName(), updateUserRequest.getLastName(), updateUserRequest.getEmail(), updateUserRequest.getPhotoUrl());
    }

    public static String postMentorProfilePayload(CreateMentorProfileRequest createMentorProfileRequest) {
        return format(loadPayload("payloads/user/post-mentor-profile.json"), createMentorProfileRequest.getMentorId(), createMentorProfileRequest.getSchoolId(), createMentorProfileRequest.getEmail(), createMentorProfileRequest.getDescription());
    }

    public static String putMentorProfilePayload(UpdateMentorProfileRequest updateMentorProfileRequest) {
        return format(loadPayload("payloads/user/put-mentor-profile.json"), updateMentorProfileRequest.getDescription());
    }

    public static String postMenteeProfilePayload(CreateMenteeProfileRequest createMenteeProfileRequest) {
        return format(loadPayload("payloads/user/post-mentee-profile.json"), createMenteeProfileRequest.getMenteeId(), createMenteeProfileRequest.getDescription());
    }

    public static String putMenteeProfilePayload(UpdateMenteeProfileRequest updateMenteeProfileRequest) {
        return format(loadPayload("payloads/user/put-mentee-profile.json"), updateMenteeProfileRequest.getDescription());
    }

    public static String postSchoolPayload(CreateSchoolRequest createSchoolRequest) {
        return format(loadPayload("payloads/school/post-school.json"), createSchoolRequest.getName(), createSchoolRequest.getDescription(), createSchoolRequest.getPhotoUrl());
    }

    public static String putSchoolPayload(UpdateSchoolRequest updateSchoolRequest) {
        return format(loadPayload("payloads/school/put-school.json"), updateSchoolRequest.getName(), updateSchoolRequest.getDescription(), updateSchoolRequest.getPhotoUrl());
    }

    public static String postAvailabilityPayload(CreateAvailabilityRequest createAvailabilityRequest) {
        return format(loadPayload("payloads/availability/post-availability.json"), createAvailabilityRequest.getMentorProfileId(), createAvailabilityRequest.getFromDateTime(), createAvailabilityRequest.getToDateTime());
    }

    public static String putAvailabilityPayload(UpdateAvailabilityRequest updateAvailabilityRequest) {
        return format(loadPayload("payloads/availability/put-availability.json"), updateAvailabilityRequest.getFromDateTime(), updateAvailabilityRequest.getToDateTime());
    }

    public static String postAppointmentPayload(Appointment appointment) {
        return format(loadPayload("payloads/appointment/post-appointment.json"), appointment.getMentorProfileId(), appointment.getMenteeProfileId(), appointment.getFromDateTime(), appointment.getToDateTime(), appointment.getDescription());
    }

    public static String putAppointmentPayload(Appointment appointment) {
        return format(loadPayload("payloads/appointment/put-appointment.json"), appointment.getFromDateTime(), appointment.getToDateTime(), appointment.getDescription(), appointment.getStatus());
    }

    public static String putAppointmentReasonPayload(AppointmentReason reason) {
        return format(loadPayload("payloads/appointment/put-appointment-reason.json"), reason.getReason());
    }
}
