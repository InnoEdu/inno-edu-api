package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.common.Constants.user;
import static inno.edu.api.common.Constants.users;
import static inno.edu.api.common.Constants.usersResource;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetUserUsingId() {
        when(userRepository.findOne(eq(user().getId()))).thenReturn(user());

        UserResource userResource = userController.get(user().getId());

        assertThat(userResource.getUser(), is(user()));
    }

    @Test
    public void shouldListAllUsers() {
        when(userRepository.findAll()).thenReturn(users());
        when(resourceBuilder.fromResources(anyListOf(UserResource.class))).thenReturn(usersResource());

        Resources<UserResource> allResources = userController.all();

        assertThat(allResources, is(usersResource()));
    }
}