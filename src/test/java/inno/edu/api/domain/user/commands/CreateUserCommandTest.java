package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.exceptions.UserNameAlreadyExistsException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.factories.UserFactory.fei;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserCommandTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserCommand createUserCommand;

    @Test
    public void shouldCallRepositoryToSaveUser() {
        ArgumentCaptor<User> argumentCaptor = forClass(User.class);

        when(userRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        User user = createUserCommand.run(fei());

        assertThat(user, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForUser() {
        ArgumentCaptor<User> argumentCaptor = forClass(User.class);

        when(userRepository.save(argumentCaptor.capture())).thenReturn(fei());

        createUserCommand.run(fei());

        assertThat(argumentCaptor.getValue().getId(), not(fei().getId()));
    }

    @Test(expected = UserNameAlreadyExistsException.class)
    public void shouldNotAllowMultipleUsersWithSameUserName() {
        when(userRepository.existsUserByUserName(fei().getUserName())).thenReturn(true);

        createUserCommand.run(fei());
    }
}