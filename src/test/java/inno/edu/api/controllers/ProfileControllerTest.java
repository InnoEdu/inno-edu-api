package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.commands.CreateProfileCommand;
import inno.edu.api.domain.profile.commands.DeleteProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateProfileCommand;
import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
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
import static inno.edu.api.support.ProfileFactory.createNewAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static inno.edu.api.support.ProfileFactory.profiles;
import static inno.edu.api.support.ProfileFactory.updateNewAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedNewAlanProfile;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private GetProfileByIdQuery getProfileByIdQuery;

    @Mock
    private UpdateProfileCommand updateProfileCommand;

    @Mock
    private CreateProfileCommand createProfileCommand;

    @Mock
    private DeleteProfileCommand deleteProfileCommand;

    @InjectMocks
    private ProfileController profileController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListProfiles() {
        when(profileRepository.findAll()).thenReturn(profiles());

        profileController.all();

        verify(resourceBuilder).wrappedFrom(eq(profiles()), any(), eq(ProfileResource.class));
    }

    @Test
    public void shouldGetProfileById() {
        when(getProfileByIdQuery.run(eq(alanProfile().getId()))).thenReturn(newAlanProfile());

        ProfileResource profileResource = profileController.get(newAlanProfile().getId());

        assertThat(profileResource.getProfile(), is(newAlanProfile()));
    }

    @Test
    public void shouldCreateNewProfiles() {
        when(createProfileCommand.run(createNewAlanProfileRequest())).thenReturn(newAlanProfile());

        ResponseEntity<Profile> entity = profileController.post(createNewAlanProfileRequest());

        assertThat(entity.getBody(), is(newAlanProfile()));
    }

    @Test
    public void shouldUpdateUser() {
        when(updateProfileCommand.run(alanProfile().getId(), updateNewAlanProfileRequest())).thenReturn(updatedNewAlanProfile());

        ResponseEntity<Profile> entity = profileController.put(alanProfile().getId(), updateNewAlanProfileRequest());

        assertThat(entity.getBody(), is(updatedNewAlanProfile()));
    }

    @Test
    public void shouldDeleteUser() {
        profileController.delete(newAlanProfile().getId());

        verify(deleteProfileCommand).run(newAlanProfile().getId());
    }
}