package inno.edu.api.domain.appointment.assertions;

import inno.edu.api.domain.appointment.exceptions.InvalidRatingRangeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.feedback;

@RunWith(MockitoJUnitRunner.class)
public class RatingInRangeAssertionTest {
    @InjectMocks
    private RatingInRangeAssertion ratingInRangeAssertion;

    @Test
    public void shouldNotThrowExceptionForValidValue() {
        ratingInRangeAssertion.run(feedback().getRating());
    }

    @Test(expected = InvalidRatingRangeException.class)
    public void shouldThrowExceptionForInvalidLowValue() {
        ratingInRangeAssertion.run(-1);
    }

    @Test(expected = InvalidRatingRangeException.class)
    public void shouldThrowExceptionForInvalidHighValue() {
        ratingInRangeAssertion.run(6);
    }
}