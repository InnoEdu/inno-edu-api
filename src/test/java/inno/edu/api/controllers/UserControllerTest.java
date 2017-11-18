package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.controllers.resources.UserResource;
import inno.edu.api.domain.profile.commands.ApproveMentorProfileByUserCommand;
import inno.edu.api.domain.user.commands.CreateUserCommand;
import inno.edu.api.domain.user.commands.LoginCommand;
import inno.edu.api.domain.user.commands.UpdateUserCommand;
import inno.edu.api.domain.user.exceptions.UserNotFoundException;
import inno.edu.api.domain.user.models.User;
import inno.edu.api.domain.user.queries.GetMenteeProfileByUserIdQuery;
import inno.edu.api.domain.user.queries.GetMentorActiveProfileByUserIdQuery;
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

import static inno.edu.api.support.UserFactory.alan;
import static inno.edu.api.support.UserFactory.alanProfile;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiCredentials;
import static inno.edu.api.support.UserFactory.feiProfile;
import static inno.edu.api.support.UserFactory.users;
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
    private ApproveMentorProfileByUserCommand approveMentorProfileByUserCommand;

    @InjectMocks
    private UserController userController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetUserById() {
        when(userRepository.findOne(eq(fei().getId()))).thenReturn(fei());

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

    @Test(expected = UserNotFoundException.class)
    public void shouldRaiseExceptionIfUserNotFound() {
        when(userRepository.findOne(any())).thenReturn(null);

        userController.get(fei().getId());
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

    @Test
    public void shouldApproveUserProfile() {
        userController.approve(fei().getId());

        verify(approveMentorProfileByUserCommand).run(fei().getId());
    }

}