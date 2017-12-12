package inno.edu.api.infrastructure.security.service;

import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowExceptionIfUsernameNotFound() {
        when(userRepository.findOneByUsername(fei().getUsername())).thenReturn(null);

        userDetailsService.loadUserByUsername(fei().getUsername());
    }

    @Test
    public void shouldReturnUserIfUsernameIsFound() {
        when(userRepository.findOneByUsername(fei().getUsername())).thenReturn(fei());

        UserDetails user = userDetailsService.loadUserByUsername(fei().getUsername());

        assertThat(user.getUsername(), is(fei().getUsername()));
        assertThat(user.getPassword(), is(fei().getPassword()));
    }
}