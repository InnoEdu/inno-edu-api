package inno.edu.api.domain.user.root.commands;

import inno.edu.api.domain.user.root.models.dtos.mappers.UpdateUserRequestMapper;
import inno.edu.api.domain.user.root.models.ApplicationUser;
import inno.edu.api.domain.user.root.queries.GetUserByIdQuery;
import inno.edu.api.domain.user.root.repositories.UserRepository;
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

        ApplicationUser applicationUser = updateUserCommand.run(fei().getId(), updateFeiRequest());

        verify(updateUserRequestMapper).setUser(updateFeiRequest(), fei());

        assertThat(applicationUser, is(updatedFei()));
    }
}