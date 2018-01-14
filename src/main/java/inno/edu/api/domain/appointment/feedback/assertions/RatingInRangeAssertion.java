package inno.edu.api.domain.appointment.feedback.assertions;

import inno.edu.api.domain.appointment.feedback.exceptions.InvalidRatingRangeException;
import inno.edu.api.infrastructure.annotations.Assertion;

@Assertion
public class RatingInRangeAssertion {
    public void run(int rating) {
        if (rating < 0 || rating > 5) {
            throw new InvalidRatingRangeException(rating);
        }
    }
}
