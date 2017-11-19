package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.fei;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUserCommandTest {
    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserCommand deleteUserCommand;

    @Test
    public void shouldCallRepositoryToDeleteUser() {
        deleteUserCommand.run(fei().getId());

        verify(userRepository).delete(fei().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteUserCommand.run(fei().getId());

        verify(userExistsAssertion).run(fei().getId());
    }
}