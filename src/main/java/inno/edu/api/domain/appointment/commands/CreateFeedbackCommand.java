package inno.edu.api.domain.appointment.commands;

import inno.edu.api.domain.appointment.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.commands.mappers.CreateFeedbackRequestMapper;
import inno.edu.api.domain.appointment.models.Feedback;
import inno.edu.api.domain.appointment.repositories.FeedbackRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateFeedbackCommand {
    private final UUIDGeneratorService uuidGeneratorService;
    private final CreateFeedbackRequestMapper createFeedbackRequestMapper;
    private final FeedbackRepository feedbackRepository;

    private final AppointmentExistsAssertion appointmentExistsAssertion;

    public CreateFeedbackCommand(UUIDGeneratorService uuidGeneratorService, CreateFeedbackRequestMapper createFeedbackRequestMapper, FeedbackRepository feedbackRepository, AppointmentExistsAssertion appointmentExistsAssertion) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createFeedbackRequestMapper = createFeedbackRequestMapper;
        this.feedbackRepository = feedbackRepository;
        this.appointmentExistsAssertion = appointmentExistsAssertion;
    }

    public Feedback run(UUID appointmentId, CreateFeedbackRequest request) {
        appointmentExistsAssertion.run(appointmentId);

        Feedback feedback = createFeedbackRequestMapper.toFeedback(request);

        feedback.setId(uuidGeneratorService.generate());
        feedback.setAppointmentId(appointmentId);

        return feedbackRepository.save(feedback);
    }
}
