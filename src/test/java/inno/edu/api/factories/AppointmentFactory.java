package inno.edu.api.factories;

import inno.edu.api.controllers.resources.AppointmentResource;
import inno.edu.api.domain.appointment.models.Appointment;
import org.springframework.hateoas.Resources;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.factories.UniversityFactory.stanford;
import static inno.edu.api.factories.UserFactory.alan;
import static inno.edu.api.factories.UserFactory.fei;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class AppointmentFactory {
    public static Appointment appointment() {
        return Appointment.builder()
                .id(fromString("f192270f-2dad-4bcd-96c3-c3765df77ce8"))
                .mentorId(fei().getId())
                .menteeId(alan().getId())
                .universityId(stanford().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 10, 9, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 10, 0, 1))
                .build();
    }

    public static Appointment otherAppointment() {
        return Appointment.builder()
                .id(fromString("42f2431a-0216-416e-b795-29292b637ec4"))
                .mentorId(fei().getId())
                .menteeId(alan().getId())
                .universityId(stanford().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 10, 10, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 11, 0, 1))
                .build();
    }

    public static Appointment updatedAppointment() {
        return Appointment.builder()
                .id(fromString("f192270f-2dad-4bcd-96c3-c3765df77ce8"))
                .mentorId(fei().getId())
                .menteeId(alan().getId())
                .universityId(stanford().getId())
                .fromDateTime(LocalDateTime.of(2019, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2019, 11, 10, 12, 0, 1))
                .build();
    }

    private static AppointmentResource appointmentResource() {
        return new AppointmentResource(appointment());
    }

    public static List<Appointment> allAppointments() {
        return newArrayList(appointment(), otherAppointment());
    }

    public static List<Appointment> feiAppointment() {
        return singletonList(appointment());
    }

    public static Resources<AppointmentResource>  feiAppointmentResources() {
        return new Resources<>(singletonList(new AppointmentResource(appointment())));
    }


    public static Resources<AppointmentResource> appointmentResources() {
        return new Resources<>(singletonList(appointmentResource()));
    }

    public static String appointmentPostPayload(Appointment appointment) {
        return format("{" + "\"mentorId\": \"%s\", " +
                "\"menteeId\": \"%s\", " +
                "\"universityId\": \"%s\", " +
                "\"fromDateTime\": \"%s\", " +
                "\"toDateTime\": \"%s\"}", appointment.getMentorId(), appointment.getMenteeId(), appointment.getUniversityId(), appointment.getFromDateTime().toString(), appointment.getToDateTime().toString());
    }

    public static String appointmentPutPayload(Appointment appointment) {
        return format("{\"fromDateTime\": \"%s\", \"toDateTime\": \"%s\"}", appointment.getFromDateTime().toString(), appointment.getToDateTime().toString());
    }

}
