package inno.edu.api.support;

import inno.edu.api.domain.appointment.feedback.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.feedback.models.Feedback;
import inno.edu.api.domain.appointment.feedback.models.FeedbackSource;
import inno.edu.api.domain.appointment.root.models.dtos.CalculateAppointmentFeeRequest;
import inno.edu.api.domain.appointment.root.models.dtos.CreateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.dtos.UpdateAppointmentRequest;
import inno.edu.api.domain.appointment.root.models.dtos.UpdateAppointmentStatusRequest;
import inno.edu.api.domain.appointment.root.models.Appointment;
import inno.edu.api.domain.appointment.root.models.dtos.SearchAppointmentsRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.ACCEPTED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.CANCELED;
import static inno.edu.api.domain.appointment.root.models.AppointmentStatus.PROPOSED;
import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static java.time.LocalDateTime.of;
import static java.util.Collections.singletonList;
import static java.util.UUID.fromString;

public class AppointmentFactory {
    public static Appointment appointment() {
        return Appointment.builder()
                .id(fromString("f192270f-2dad-4bcd-96c3-c3765df77ce8"))
                .mentorProfileId(feiProfile().getId())
                .menteeProfileId(alanProfile().getId())
                .fromDateTime(of(2017, 11, 10, 9, 0, 1))
                .toDateTime(of(2017, 11, 10, 10, 0, 1))
                .description("My great first appointment.")
                .fee(new BigDecimal(10.5))
                .status(PROPOSED)
                .build();
    }

    public static Appointment conflictAppointment() {
        return Appointment.builder()
                .id(fromString("22f81159-e211-4b95-9416-6648c339ce3a"))
                .mentorProfileId(feiProfile().getId())
                .menteeProfileId(alanProfile().getId())
                .fromDateTime(of(2017, 11, 10, 9, 0, 5))
                .toDateTime(of(2017, 11, 10, 10, 0, 5))
                .description("conflict")
                .fee(new BigDecimal(10.5))
                .status(PROPOSED)
                .build();
    }

    public static Appointment appointmentToDelete() {
        return appointment().toBuilder()
                .id(fromString("2bb42516-808d-4b2e-940c-2eba440ac378"))
                .build();
    }

    public static CreateAppointmentRequest createAppointmentRequest() {
        return CreateAppointmentRequest.builder()
                .mentorProfileId(feiProfile().getId())
                .menteeProfileId(alanProfile().getId())
                .fromDateTime(of(2017, 11, 10, 9, 0, 1))
                .toDateTime(of(2017, 11, 10, 10, 0, 1))
                .description("My great first appointment.")
                .build();
    }

    public static CalculateAppointmentFeeRequest calculateAppointmentFeeRequest() {
        return CalculateAppointmentFeeRequest.builder()
                .mentorProfileId(otherAppointment().getMentorProfileId())
                .fromDateTime(otherAppointment().getFromDateTime())
                .toDateTime(otherAppointment().getToDateTime())
                .build();
    }

    public static Appointment otherAppointment() {
        return Appointment.builder()
                .id(fromString("42f2431a-0216-416e-b795-29292b637ec4"))
                .mentorProfileId(feiProfile().getId())
                .menteeProfileId(alanProfile().getId())
                .fromDateTime(of(2017, 11, 10, 10, 0, 1))
                .toDateTime(of(2017, 11, 10, 11, 0, 1))
                .description("My great second appointment.")
                .fee(new BigDecimal(10.5))
                .status(ACCEPTED)
                .build();
    }

    public static Appointment newAppointment() {
        return Appointment.builder()
                .mentorProfileId(feiProfile().getId())
                .menteeProfileId(alanProfile().getId())
                .fromDateTime(of(2017, 11, 10, 10, 0, 1))
                .toDateTime(of(2017, 11, 10, 11, 0, 1))
                .description("My new appointment.")
                .build();
    }

    public static Appointment updatedAppointment() {
        return appointment().toBuilder()
                .fromDateTime(of(2019, 11, 9, 12, 0, 1))
                .toDateTime(of(2019, 11, 10, 12, 0, 1))
                .description("My great updated appointment.")
                .fee(new BigDecimal(252))
                .build();
    }

    public static UpdateAppointmentRequest updateAppointmentRequest() {
        return UpdateAppointmentRequest.builder()
                .fromDateTime(of(2019, 11, 9, 12, 0, 1))
                .toDateTime(of(2019, 11, 10, 12, 0, 1))
                .description("My great updated appointment.")
                .build();
    }

    public static UpdateAppointmentStatusRequest updateAppointmentStatusRequest() {
        return UpdateAppointmentStatusRequest.builder()
                .reason("Reason for appointment.")
                .status(ACCEPTED)
                .build();
    }

    public static Feedback feedback() {
        return Feedback.builder()
                .id(fromString("bd06f884-b126-4be8-b637-758519dea5a5"))
                .appointmentId(appointment().getId())
                .description("Great session")
                .rating(5)
                .source(FeedbackSource.MENTEE)
                .build();
    }

    public static Feedback newFeedback(UUID id, UUID appointmentId) {
        return feedback().toBuilder()
                .id(id)
                .appointmentId(appointmentId)
                .build();
    }

    public static CreateFeedbackRequest createFeedbackRequest() {
        return CreateFeedbackRequest.builder()
                .description(feedback().getDescription())
                .rating(feedback().getRating())
                .source(feedback().getSource())
                .build();
    }

    public static List<Appointment> appointments() {
        return newArrayList(appointment(), otherAppointment());
    }

    public static List<Appointment> proposedAppointments() {
        return newArrayList(appointment());
    }

    public static List<Feedback> feedbacks() {
        return singletonList(feedback());
    }

    public static SearchAppointmentsRequest searchAppointmentsByStatus() {
        return SearchAppointmentsRequest.builder()
                .status(newArrayList(PROPOSED, CANCELED))
                .build();
    }
}
