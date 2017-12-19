package inno.edu.api.domain.appointment.queries;

import inno.edu.api.domain.appointment.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.repositories.AppointmentRepository;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import inno.edu.api.infrastructure.annotations.Query;

import java.util.List;
import java.util.UUID;

@Query
public class GetFeedbacksByAppointmentByIdQuery {
    private final AppointmentExistsAssertion appointmentExistsAssertion;
    private final FeedbackRepository feedbackRepository;

    public GetFeedbacksByAppointmentByIdQuery(AppointmentRepository appointmentRepository, AppointmentExistsAssertion appointmentExistsAssertion, FeedbackRepository feedbackRepository) {
        this.appointmentExistsAssertion = appointmentExistsAssertion;
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> run(UUID appointmentId) {
        appointmentExistsAssertion.run(appointmentId);

        return feedbackRepository.findByAppointmentId(appointmentId);
    }
}
