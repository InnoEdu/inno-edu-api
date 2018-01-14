package inno.edu.api.domain.appointment.feedback.commands;

import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.feedback.assertions.RatingInRangeAssertion;
import inno.edu.api.domain.appointment.feedback.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.feedback.commands.mappers.CreateFeedbackRequestMapper;
import inno.edu.api.domain.appointment.feedback.models.Feedback;
import inno.edu.api.domain.appointment.feedback.respositories.FeedbackRepository;
import inno.edu.api.infrastructure.annotations.Command;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;

import java.util.UUID;

@Command
public class CreateFeedbackCommand {
    private final UUIDGeneratorService uuidGeneratorService;
    private final CreateFeedbackRequestMapper createFeedbackRequestMapper;
    private final FeedbackRepository feedbackRepository;

    private final AppointmentExistsAssertion appointmentExistsAssertion;
    private final RatingInRangeAssertion ratingInRangeAssertion;

    public CreateFeedbackCommand(UUIDGeneratorService uuidGeneratorService, CreateFeedbackRequestMapper createFeedbackRequestMapper, FeedbackRepository feedbackRepository, AppointmentExistsAssertion appointmentExistsAssertion, RatingInRangeAssertion ratingInRangeAssertion) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.createFeedbackRequestMapper = createFeedbackRequestMapper;
        this.feedbackRepository = feedbackRepository;
        this.appointmentExistsAssertion = appointmentExistsAssertion;
        this.ratingInRangeAssertion = ratingInRangeAssertion;
    }

    public Feedback run(UUID appointmentId, CreateFeedbackRequest request) {
        appointmentExistsAssertion.run(appointmentId);
        ratingInRangeAssertion.run(request.getRating());

        Feedback feedback = createFeedbackRequestMapper.toFeedback(request);

        feedback.setId(uuidGeneratorService.generate());
        feedback.setAppointmentId(appointmentId);

        return feedbackRepository.save(feedback);
    }
}
