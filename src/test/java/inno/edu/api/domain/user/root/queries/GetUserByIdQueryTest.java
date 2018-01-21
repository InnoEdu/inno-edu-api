package inno.edu.api.domain.user.root.queries;

import inno.edu.api.domain.user.root.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.root.models.ApplicationUser;
import inno.edu.api.domain.user.root.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetUserByIdQueryTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByIdQuery getUserByIdQuery;

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionIfUserDoesNotExist() {
        when(userRepository.findOne(fei().getId())).thenReturn(null);

        getUserByIdQuery.run(fei().getId());
    }

    @Test
    public void shouldReturnUser() {
        when(userRepository.findOne(fei().getId())).thenReturn(fei());

        ApplicationUser applicationUser = getUserByIdQuery.run(fei().getId());

        assertThat(applicationUser, is(fei()));
    }
}