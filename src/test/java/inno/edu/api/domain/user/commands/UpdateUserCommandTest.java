package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.common.Builders.updatedUser;
import static inno.edu.api.common.Builders.user;
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
        when(userRepository.findOne(user().getId())).thenReturn(user());
        when(userRepository.save(user())).thenReturn(updatedUser());

        User user = updateUserCommand.run(user().getId(), user());

        assertThat(user, is(updatedUser()));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldRaiseExceptionIfUserDoesNotExists() {
        when(userRepository.findOne(user().getId())).thenThrow(new UserNotFoundException(user().getId()));

        updateUserCommand.run(randomUUID(), user());
    }
}