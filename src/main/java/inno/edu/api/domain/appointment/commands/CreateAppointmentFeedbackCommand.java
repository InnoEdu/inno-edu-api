package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.commands.dtos.CreateAppointmentFeedbackRequest;
import inno.edu.api.domain.appointment.commands.mappers.CreateAppointmentFeedbackRequestMapper;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateAppointmentFeedbackCommand {
    private final UUIDGeneratorService uuidGeneratorService;
    private final CreateAppointmentFeedbackRequestMapper createAppointmentFeedbackRequestMapper;
    private final FeedbackRepository feedbackRepository;

    private final AppointmentExistsAssertion appointmentExistsAssertion;

    public CreateAppointmentFeedbackCommand(UUIDGeneratorService uuidGeneratorService, CreateAppointmentFeedbackRequestMapper createAppointmentFeedbackRequestMapper, FeedbackRepository feedbackRepository, AppointmentExistsAssertion appointmentExistsAssertion) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createAppointmentFeedbackRequestMapper = createAppointmentFeedbackRequestMapper;
        this.feedbackRepository = feedbackRepository;
        this.appointmentExistsAssertion = appointmentExistsAssertion;
    }

    public Feedback run(UUID appointmentId, CreateAppointmentFeedbackRequest request) {
        appointmentExistsAssertion.run(appointmentId);

        Feedback feedback = createAppointmentFeedbackRequestMapper.toFeedback(request);

        feedback.setId(uuidGeneratorService.generate());
        feedback.setAppointmentId(appointmentId);

        return feedbackRepository.save(feedback);
    }
}
