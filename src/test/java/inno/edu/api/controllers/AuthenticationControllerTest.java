package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.commands.LoginCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiCredentials;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {
    @Mock
    private LoginCommand loginCommand;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldSignInUser() {
        when(loginCommand.run(feiCredentials())).thenReturn(fei());

        UserResource userResource = authenticationController.login(feiCredentials());

        assertThat(userResource.getUser(), is(fei()));
    }
}