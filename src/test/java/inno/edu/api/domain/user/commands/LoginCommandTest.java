package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.exceptions.InvalidUsernameOrPasswordException;
import inno.edu.api.domain.user.models.ApplicationUser;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiCredentials;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginCommand loginCommand;

    @Test
    public void shouldReturnUserIfCredentialsAreValid() {
        when(userRepository.findOneByUsernameAndPassword(fei().getUsername(), fei().getPassword()))
                .thenReturn(fei());

        ApplicationUser applicationUser = loginCommand.run(feiCredentials());

        assertThat(applicationUser, is(fei()));
    }

    @Test(expected = InvalidUsernameOrPasswordException.class)
    public void shouldThrowExceptionIfCredentialsAreNotValid() {
        when(userRepository.findOneByUsernameAndPassword(fei().getUsername(), fei().getPassword()))
                .thenReturn(null);

        loginCommand.run(feiCredentials());
    }
}