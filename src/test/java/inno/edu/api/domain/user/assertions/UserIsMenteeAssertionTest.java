package inno.edu.api.domain.user.assertions;

import inno.edu.api.domain.user.exceptions.UserIsNotMenteeException;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.alan;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserIsMenteeAssertionTest {
    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserIsMenteeAssertion userIsMenteeAssertion;

    @Test(expected = UserIsNotMenteeException.class)
    public void shouldThrowExceptionIfUserIsNotMentee() {
        when(userRepository.existsByIdAndIsMentorIsFalse(alan().getId())).thenReturn(false);

        userIsMenteeAssertion.run(alan().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfUserIsMentee() {
        when(userRepository.existsByIdAndIsMentorIsFalse(alan().getId())).thenReturn(true);

        userIsMenteeAssertion.run(alan().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        when(userRepository.existsByIdAndIsMentorIsFalse(alan().getId())).thenReturn(true);

        userIsMenteeAssertion.run(alan().getId());

        verify(userExistsAssertion).run(alan().getId());
    }
}