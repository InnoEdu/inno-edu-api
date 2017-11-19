package inno.edu.api.domain.user.assertions;

import inno.edu.api.domain.user.exceptions.UserIsNotMentorException;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.fei;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserIsMentorAssertionTest {
    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserIsMentorAssertion userIsMentorAssertion;

    @Test(expected = UserIsNotMentorException.class)
    public void shouldThrowExceptionIfUserIsNotMentor() {
        when(userRepository.existsByIdAndIsMentorIsTrue(fei().getId())).thenReturn(false);

        userIsMentorAssertion.run(fei().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfUserIsMentor() {
        when(userRepository.existsByIdAndIsMentorIsTrue(fei().getId())).thenReturn(true);

        userIsMentorAssertion.run(fei().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        when(userRepository.existsByIdAndIsMentorIsTrue(fei().getId())).thenReturn(true);

        userIsMentorAssertion.run(fei().getId());

        verify(userExistsAssertion).run(fei().getId());
    }

}