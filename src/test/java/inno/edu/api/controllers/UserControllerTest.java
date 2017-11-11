package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.factories.UserFactory.fei;
import static inno.edu.api.factories.UserFactory.users;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateUserCommand createUserCommand;

    @Mock
    private UpdateUserCommand updateUserCommand;

    @InjectMocks
    private UserController userController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetUserUsingId() {
        when(userRepository.findOne(eq(fei().getId()))).thenReturn(fei());

        UserResource userResource = userController.get(fei().getId());

        assertThat(userResource.getUser(), is(fei()));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldRaiseExceptionIfUserNotFound() {
        when(userRepository.findOne(any())).thenReturn(null);

        userController.get(fei().getId());
    }

    @Test
    public void shouldListAllUsers() {
        when(userRepository.findAll()).thenReturn(users());

        userController.all();

        verify(resourceBuilder).from(eq(users()), any());
    }

    @Test
    public void shouldCreateNewUser() {
        ArgumentCaptor<User> argumentCaptor = forClass(User.class);
        when(createUserCommand.run(argumentCaptor.capture())).thenReturn(fei());

        userController.post(fei());

        verify(createUserCommand).run(argumentCaptor.capture());
    }

    @Test
    public void shouldUpdateUser() {
        when(updateUserCommand.run(fei().getId(), fei())).thenReturn(fei());

        userController.put(fei().getId(), fei());

        verify(updateUserCommand).run(fei().getId(), fei());
    }

    @Test
    public void shouldUDeleteUser() {
        when(userRepository.exists(fei().getId())).thenReturn(true);

        userController.delete(fei().getId());

        verify(userRepository).delete(fei().getId());
    }

}