package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.user.models.ApplicationUser;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.users;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetUsersQueryTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUsersQuery getUsersQuery;

    @Test
    public void shouldReturnUsers() {
        when(userRepository.findAll()).thenReturn(users());

        List<ApplicationUser> users = getUsersQuery.run(fei().getId());

        assertThat(users, is(users()));
    }
}