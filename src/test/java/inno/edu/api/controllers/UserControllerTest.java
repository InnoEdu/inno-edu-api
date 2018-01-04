package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.profile.queries.GetProfileByUserIdQuery;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.DeleteUserCommand;
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.commands.dtos.LoginResponse;
import inno.edu.api.domain.user.models.ApplicationUser;
import inno.edu.api.domain.user.queries.GetUserByIdQuery;
import inno.edu.api.domain.user.repositories.UserRepository;
import inno.edu.api.support.ProfileFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.createFeiRequest;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiCredentials;
import static inno.edu.api.support.UserFactory.feiLoginResponse;
import static inno.edu.api.support.UserFactory.updateFeiRequest;
import static inno.edu.api.support.UserFactory.updatedFei;
import static inno.edu.api.support.UserFactory.users;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
    private GetUserByIdQuery getUserByIdQuery;

    @Mock
    private GetProfileByUserIdQuery getProfileByUserIdQuery;

    @Mock
    private CreateUserCommand createUserCommand;

    @Mock
    private UpdateUserCommand updateUserCommand;

    @Mock
    private DeleteUserCommand deleteUserCommand;

    @Mock
    private LoginCommand loginCommand;

    @InjectMocks
    private UserController userController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetUserById() {
        when(getUserByIdQuery.run(eq(fei().getId()))).thenReturn(fei());

        UserResource userResource = userController.get(fei().getId());

        assertThat(userResource.getUser(), is(fei()));
    }

    @Test
    public void shouldGetProfileByUserId() {
        when(getProfileByUserIdQuery.run(alan().getId())).thenReturn(ProfileFactory.alanProfile());

        userController.getProfile(alan().getId());

        verify(getProfileByUserIdQuery).run(alan().getId());
    }

    @Test
    public void shouldListUsers() {
        when(userRepository.findAll()).thenReturn(users());

        userController.all();

        verify(resourceBuilder).wrappedFrom(eq(users()), any(), eq(UserResource.class));
    }

    @Test
    public void shouldCreateNewUser() {
        when(createUserCommand.run(createFeiRequest())).thenReturn(fei());
        when(loginCommand.run(feiCredentials())).thenReturn(feiLoginResponse());

        ResponseEntity<LoginResponse> entity = userController.post(createFeiRequest());
        assertThat(entity.getBody(), is(feiLoginResponse()));
    }

    @Test
    public void shouldUpdateUser() {
        when(updateUserCommand.run(fei().getId(), updateFeiRequest())).thenReturn(updatedFei());

        ResponseEntity<ApplicationUser> entity = userController.put(fei().getId(), updateFeiRequest());

        assertThat(entity.getBody(), is(updatedFei()));
    }

    @Test
    public void shouldDeleteUser() {
        userController.delete(fei().getId());

        verify(deleteUserCommand).run(fei().getId());
    }
}