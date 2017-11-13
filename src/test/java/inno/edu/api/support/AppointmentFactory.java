package inno.edu.api.support;

import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.models.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.support.UniversityFactory.stanford;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.feiProfile;
import static java.util.UUID.fromString;

public class AppointmentFactory {
    public static Appointment appointment() {
        return Appointment.builder()
                .id(fromString("f192270f-2dad-4bcd-96c3-c3765df77ce8"))
                .mentorProfileId(feiProfile().getId())
                .menteeId(alan().getId())
                .universityId(stanford().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 10, 9, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 10, 0, 1))
                .status(AppointmentStatus.PROPOSED)
                .build();
    }

    public static Appointment otherAppointment() {
        return Appointment.builder()
                .id(fromString("42f2431a-0216-416e-b795-29292b637ec4"))
                .mentorProfileId(feiProfile().getId())
                .menteeId(alan().getId())
                .universityId(stanford().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 10, 10, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 11, 0, 1))
                .status(AppointmentStatus.ACCEPTED)
                .build();
    }

    public static Appointment updatedAppointment() {
        return Appointment.builder()
                .id(fromString("f192270f-2dad-4bcd-96c3-c3765df77ce8"))
                .mentorProfileId(feiProfile().getId())
                .menteeId(alan().getId())
                .universityId(stanford().getId())
                .fromDateTime(LocalDateTime.of(2019, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2019, 11, 10, 12, 0, 1))
                .status(AppointmentStatus.REJECTED)
                .build();
    }

    public static List<Appointment> appointments() {
        return newArrayList(appointment(), otherAppointment());
    }

    public static List<Appointment> proposedAppointments() {
        return newArrayList(appointment());
    }
}
