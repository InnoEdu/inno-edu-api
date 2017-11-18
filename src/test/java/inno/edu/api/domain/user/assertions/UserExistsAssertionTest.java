package inno.edu.api.domain.user.assertions;

import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.fei;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserExistsAssertionTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserExistsAssertion userExistsAssertion;

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionIfUserDoesNotExist() {
        when(userRepository.exists(fei().getId())).thenReturn(false);

        userExistsAssertion.run(fei().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfUserExists() {
        when(userRepository.exists(fei().getId())).thenReturn(true);

        userExistsAssertion.run(fei().getId());
    }
}