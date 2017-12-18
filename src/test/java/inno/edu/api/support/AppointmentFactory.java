package inno.edu.api.support;

import inno.edu.api.domain.appointment.commands.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.commands.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.commands.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.models.Appointment;
import inno.edu.api.domain.appointment.commands.dtos.AppointmentReason;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.domain.appointment.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static java.util.UUID.fromString;

public class AppointmentFactory {
    public static Appointment appointment() {
        return Appointment.builder()
                .id(fromString("f192270f-2dad-4bcd-96c3-c3765df77ce8"))
                .mentorProfileId(feiProfile().getId())
                .menteeProfileId(alanProfile().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 10, 9, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 10, 0, 1))
                .description("My great first appointment.")
                .fee(new BigDecimal(10.5))
                .status(PROPOSED)
                .build();
    }

    public static CreateAppointmentRequest createAppointmentRequest() {
        return CreateAppointmentRequest.builder()
                .mentorProfileId(feiProfile().getId())
                .menteeProfileId(alanProfile().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 10, 9, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 10, 0, 1))
                .description("My great first appointment.")
                .build();
    }

    public static CalculateAppointmentFeeRequest calculateAppointmentFeeRequest() {
        return CalculateAppointmentFeeRequest.builder()
                .mentorProfileId(appointment().getMentorProfileId())
                .fromDateTime(appointment().getFromDateTime())
                .toDateTime(appointment().getToDateTime())
                .build();
    }

    public static Appointment otherAppointment() {
        return Appointment.builder()
                .id(fromString("42f2431a-0216-416e-b795-29292b637ec4"))
                .mentorProfileId(feiProfile().getId())
                .menteeProfileId(alanProfile().getId())
                .fromDateTime(LocalDateTime.of(2017, 11, 10, 10, 0, 1))
                .toDateTime(LocalDateTime.of(2017, 11, 10, 11, 0, 1))
                .description("My great second appointment.")
                .fee(new BigDecimal(20.5))
                .status(ACCEPTED)
                .build();
    }

    public static Appointment updatedAppointment() {
        return appointment().toBuilder()
                .fromDateTime(LocalDateTime.of(2019, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2019, 11, 10, 12, 0, 1))
                .description("My great updated appointment.")
                .build();
    }

    public static UpdateAppointmentRequest updateAppointmentRequest() {
        return UpdateAppointmentRequest.builder()
                .fromDateTime(LocalDateTime.of(2019, 11, 9, 12, 0, 1))
                .toDateTime(LocalDateTime.of(2019, 11, 10, 12, 0, 1))
                .description("My great updated appointment.")
                .build();
    }

    public static AppointmentReason reason() {
        return AppointmentReason.builder().reason("Reason for appointment.").build();
    }

    public static AppointmentReason emptyReason() {
        return AppointmentReason.builder().build();
    }

    public static List<Appointment> appointments() {
        return newArrayList(appointment(), otherAppointment());
    }

    public static List<Appointment> proposedAppointments() {
        return newArrayList(appointment());
    }
}
