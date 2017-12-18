package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.commands.dtos.CreateUserRequest;
import inno.edu.api.domain.user.commands.mappers.CreateUserRequestMapper;
import inno.edu.api.domain.user.exceptions.PasswordMismatchException;
import inno.edu.api.domain.user.exceptions.UsernameAlreadyExistsException;
import inno.edu.api.domain.user.models.ApplicationUser;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.createFeiRequest;
import static inno.edu.api.support.UserFactory.newFei;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateUserRequestMapper createUserRequestMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserCommand createUserCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());
        when(createUserRequestMapper.toUser(createFeiRequest())).thenReturn(newFei(null));
    }

    @Test
    public void shouldCallRepositoryToSaveUser() {
        ApplicationUser newUser = newFei(uuidGeneratorService.generate());
        when(userRepository.save(newUser)).thenReturn(newUser);

        ApplicationUser savedUser = createUserCommand.run(createFeiRequest());
        assertThat(savedUser, is(newUser));
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void shouldNotAllowMultipleUsersWithSameUsername() {
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