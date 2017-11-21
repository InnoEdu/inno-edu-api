package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.commands.mappers.UpdateUserRequestMapper;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.queries.GetUserByIdQuery;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.updateFeiRequest;
import static inno.edu.api.support.UserFactory.updatedFei;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUserCommandTest {
    @Mock
    private UpdateUserRequestMapper updateUserRequestMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GetUserByIdQuery getUserByIdQuery;

    @InjectMocks
    private UpdateUserCommand updateUserCommand;

    @Test
    public void shouldReturnUpdatedUser() {
        when(getUserByIdQuery.run(fei().getId())).thenReturn(fei());
        when(userRepository.save(fei())).thenReturn(updatedFei());

        User user = updateUserCommand.run(fei().getId(), updateFeiRequest());

        verify(updateUserRequestMapper).setUser(updateFeiRequest(), fei());

        assertThat(user, is(updatedFei()));
    }
}