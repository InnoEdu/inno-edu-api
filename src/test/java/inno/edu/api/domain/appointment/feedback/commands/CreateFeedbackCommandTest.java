package inno.edu.api.domain.appointment.feedback.commands;

import inno.edu.api.domain.appointment.feedback.commands.CreateFeedbackCommand;
import inno.edu.api.domain.appointment.root.assertions.AppointmentExistsAssertion;
import inno.edu.api.domain.appointment.feedback.assertions.RatingInRangeAssertion;
import inno.edu.api.domain.appointment.feedback.commands.mappers.CreateFeedbackRequestMapper;
import inno.edu.api.domain.appointment.feedback.models.Feedback;
import inno.edu.api.domain.appointment.feedback.respositories.FeedbackRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AppointmentFactory.appointment;
import static inno.edu.api.support.AppointmentFactory.createFeedbackRequest;
import static inno.edu.api.support.AppointmentFactory.feedback;
import static inno.edu.api.support.AppointmentFactory.newFeedback;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateFeedbackCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateFeedbackRequestMapper createFeedbackRequestMapper;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private AppointmentExistsAssertion appointmentExistsAssertion;

    @Mock
    private RatingInRangeAssertion ratingInRangeAssertion;

    @InjectMocks
    private CreateFeedbackCommand createFeedbackCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createFeedbackRequestMapper.toFeedback(createFeedbackRequest()))
          .thenReturn(newFeedback(null, appointment().getId()));
    }

    @Test
    public void shouldSaveNewAppointment() {
        Feedback newFeedback = newFeedback(uuidGeneratorService.generate(), appointment().getId());

        when(feedbackRepository.save(newFeedback)).thenReturn(newFeedback);

        Feedback feedback = createFeedbackCommand.run(appointment().getId(), createFeedbackRequest());
        assertThat(feedback, is(newFeedback));
    }

    @Test
    public void shouldRunAllAssertions() {
        createFeedbackCommand.run(appointment().getId(), createFeedbackRequest());

        verify(appointmentExistsAssertion).run(appointment().getId());
        verify(ratingInRangeAssertion).run(feedback().getRating());
    }
}