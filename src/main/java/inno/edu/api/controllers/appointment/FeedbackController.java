package inno.edu.api.controllers.appointment;

import inno.edu.api.domain.appointment.root.models.resources.FeedbackResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.appointment.feedback.commands.CreateFeedbackCommand;
import inno.edu.api.domain.appointment.feedback.commands.DeleteFeedbackCommand;
import inno.edu.api.domain.appointment.feedback.commands.dtos.CreateFeedbackRequest;
import inno.edu.api.domain.appointment.feedback.models.Feedback;
import inno.edu.api.domain.appointment.feedback.queries.GetFeedbackByIdQuery;
import inno.edu.api.domain.appointment.feedback.queries.GetFeedbacksByAppointmentByIdQuery;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static inno.edu.api.infrastructure.configuration.StaticConstants.APPLICATION_HAL_JSON;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/appointments", produces = APPLICATION_HAL_JSON)
public class FeedbackController {
    private final ResourceBuilder resourceBuilder;

    private final CreateFeedbackCommand createFeedbackCommand;
    private final DeleteFeedbackCommand deleteFeedbackCommand;
    private final GetFeedbacksByAppointmentByIdQuery getFeedbacksByAppointmentByIdQuery;
    private final GetFeedbackByIdQuery getFeedbackByIdQuery;

    public FeedbackController(ResourceBuilder resourceBuilder, CreateFeedbackCommand createFeedbackCommand, DeleteFeedbackCommand deleteFeedbackCommand, GetFeedbacksByAppointmentByIdQuery getFeedbacksByAppointmentByIdQuery, GetFeedbackByIdQuery getFeedbackByIdQuery) {
        this.resourceBuilder = resourceBuilder;
        this.createFeedbackCommand = createFeedbackCommand;
        this.deleteFeedbackCommand = deleteFeedbackCommand;
        this.getFeedbacksByAppointmentByIdQuery = getFeedbacksByAppointmentByIdQuery;
        this.getFeedbackByIdQuery = getFeedbackByIdQuery;
    }

    @PostMapping("/{id}/feedbacks")
    public ResponseEntity<Feedback> postFeedback(@PathVariable UUID id, @Valid @RequestBody CreateFeedbackRequest request) {
        FeedbackResource feedbackResource = new FeedbackResource(createFeedbackCommand.run(id, request));
        return feedbackResource.toCreated();
    }

    @GetMapping("/{id}/feedbacks")
    public Resources<Object> allFeedbacks(@PathVariable UUID id) {
        List<Feedback> feedbacks = getFeedbacksByAppointmentByIdQuery.run(id);
        return resourceBuilder.wrappedFrom(feedbacks, FeedbackResource::new, FeedbackResource.class);
    }

    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable UUID id) {
        deleteFeedbackCommand.run(id);
        return noContent().build();
    }

    @GetMapping("/feedbacks/{id}")
    public FeedbackResource getFeedback(@PathVariable UUID id) {
        return new FeedbackResource(getFeedbackByIdQuery.run(id));
    }
}
