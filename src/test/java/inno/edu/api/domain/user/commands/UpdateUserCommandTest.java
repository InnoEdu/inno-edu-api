package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.fei;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUserCommandTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserCommand updateUserCommand;

    @Test
    public void shouldReturnUpdatedUser() {
        when(userRepository.findOne(fei().getId())).thenReturn(fei());
        when(userRepository.save(fei())).thenReturn(alan());

        User user = updateUserCommand.run(fei().getId(), fei());

        assertThat(user, is(alan()));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldRaiseExceptionIfUserDoesNotExist() {
        when(userRepository.findOne(fei().getId())).thenThrow(new UserNotFoundException(fei().getId()));

        updateUserCommand.run(randomUUID(), fei());
    }
}