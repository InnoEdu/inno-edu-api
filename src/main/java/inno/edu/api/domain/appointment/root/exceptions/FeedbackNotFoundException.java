package inno.edu.api.domain.appointment.root.exceptions;

import java.util.UUID;

public class FeedbackNotFoundException extends RuntimeException {
    public FeedbackNotFoundException(UUID feedbackId) {
        super("Could not find feedback '" + feedbackId.toString() + "'.");
    }
}