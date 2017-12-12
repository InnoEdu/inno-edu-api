package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.commands.mappers.CreateUserRequestMapper;
import inno.edu.api.domain.user.exceptions.PasswordMismatchException;
import inno.edu.api.domain.user.exceptions.UsernameAlreadyExistsException;
import inno.edu.api.domain.user.models.ApplicationUser;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.createFeiRequest;
import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserCommandTest {
    @Mock
    private CreateUserRequestMapper createUserRequestMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserCommand createUserCommand;

    @Before
    public void setUp() {
        when(createUserRequestMapper.toUser(createFeiRequest())).thenReturn(fei());
    }

    @Test
    public void shouldCallRepositoryToSaveUser() {
        ArgumentCaptor<ApplicationUser> argumentCaptor = forClass(ApplicationUser.class);

        when(userRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        ApplicationUser applicationUser = createUserCommand.run(createFeiRequest());

        assertThat(applicationUser, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForUser() {
        ArgumentCaptor<ApplicationUser> argumentCaptor = forClass(ApplicationUser.class);

        when(userRepository.save(argumentCaptor.capture())).thenReturn(fei());

        createUserCommand.run(createFeiRequest());

        assertThat(argumentCaptor.getValue().getId(), not(fei().getId()));
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void shouldNotAllowMultipleUsersWithSameUserName() {
        when(userRepository.existsByUsername(createFeiRequest().getUsername())).thenReturn(true);

        createUserCommand.run(createFeiRequest());
    }

    @Test(expected = PasswordMismatchException.class)
    public void shouldNotAllowPasswordMismatch() {
        CreateUserRequest request = createFeiRequest()
                .toBuilder()
                .confirmPassword("otherPassword")
                .build();

        when(userRepository.existsByUsername(createFeiRequest().getUsername())).thenReturn(true);

        createUserCommand.run(request);
    }
}