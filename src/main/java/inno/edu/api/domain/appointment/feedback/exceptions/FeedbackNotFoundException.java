package inno.edu.api.domain.appointment.feedback.exceptions;

import java.util.UUID;

public class FeedbackNotFoundException extends RuntimeException {
    public FeedbackNotFoundException(UUID feedbackId) {
        super("Could not find feedback '" + feedbackId.toString() + "'.");
    }
}