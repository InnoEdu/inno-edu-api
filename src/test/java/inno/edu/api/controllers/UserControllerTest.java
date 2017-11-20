package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.profile.commands.UpdateMentorProfileStatusByUserCommand;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByUserIdQuery;
import inno.edu.api.domain.profile.queries.GetMentorActiveProfileByUserIdQuery;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.DeleteUserCommand;
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.queries.GetUserByIdQuery;
import inno.edu.api.domain.user.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.createFeiRequest;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiCredentials;
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
    private GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;

    @Mock
    private GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery;

    @Mock
    private LoginCommand loginCommand;

    @Mock
    private CreateUserCommand createUserCommand;

    @Mock
    private UpdateUserCommand updateUserCommand;

    @Mock
    private DeleteUserCommand deleteUserCommand;

    @Mock
    private UpdateMentorProfileStatusByUserCommand updateMentorProfileStatusByUserCommand;

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
    public void shouldGetMentorProfileByUserId() {
        when(userRepository.existsByIdAndIsMentorIsTrue(fei().getId())).thenReturn(true);
        when(getMentorActiveProfileByUserIdQuery.run(fei().getId())).thenReturn(feiProfile());

        userController.getProfile(fei().getId());

        verify(getMentorActiveProfileByUserIdQuery).run(fei().getId());
    }

    @Test
    public void shouldGetMenteeProfileByUserId() {
        when(userRepository.existsByIdAndIsMentorIsTrue(eq(alan().getId()))).thenReturn(false);
        when(getMenteeProfileByUserIdQuery.run(alan().getId())).thenReturn(alanProfile());

        userController.getProfile(alan().getId());

        verify(getMenteeProfileByUserIdQuery).run(alan().getId());
    }

    @Test
    public void shouldListUsers() {
        when(userRepository.findAll()).thenReturn(users());

        userController.all();

        verify(resourceBuilder).from(eq(users()), any());
    }

    @Test
    public void shouldSignInUser() {
        when(loginCommand.run(feiCredentials())).thenReturn(fei());

        UserResource userResource = userController.login(feiCredentials());

        assertThat(userResource.getUser(), is(fei()));
    }

    @Test
    public void shouldCreateNewUser() {
        when(createUserCommand.run(createFeiRequest())).thenReturn(fei());

        ResponseEntity<User> entity = userController.post(createFeiRequest());

        assertThat(entity.getBody(), is(fei()));
    }

    @Test
    public void shouldUpdateUser() {
        when(updateUserCommand.run(fei().getId(), updateFeiRequest())).thenReturn(updatedFei());

        ResponseEntity<User> entity = userController.put(fei().getId(), updateFeiRequest());

        assertThat(entity.getBody(), is(updatedFei()));
    }

    @Test
    public void shouldDeleteUser() {
        userController.delete(fei().getId());

        verify(deleteUserCommand).run(fei().getId());
    }

    @Test
    public void shouldApproveUserProfile() {
        userController.approve(fei().getId());

        verify(updateMentorProfileStatusByUserCommand).run(fei().getId(), ProfileStatus.ACTIVE);
    }

    @Test
    public void shouldRejectUserProfile() {
        userController.reject(fei().getId());

        verify(updateMentorProfileStatusByUserCommand).run(fei().getId(), ProfileStatus.REJECTED);
    }
}