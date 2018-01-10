package inno.edu.api.controllers;

import inno.edu.api.controllers.profile.ProfileController;
import inno.edu.api.controllers.profile.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.root.commands.CreateProfileCommand;
import inno.edu.api.domain.profile.root.commands.DeleteProfileCommand;
import inno.edu.api.domain.profile.root.commands.UpdateProfileCommand;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.root.queries.GetProfilesQuery;
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
import static inno.edu.api.support.ProfileFactory.createAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.profiles;
import static inno.edu.api.support.ProfileFactory.updateAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.updatedAlanProfile;
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
    private GetProfilesQuery getProfilesQuery;

    @Mock
    private GetProfileByIdQuery getProfileByIdQuery;

    @Mock
    private CreateProfileCommand createProfileCommand;

    @Mock
    private DeleteProfileCommand deleteProfileCommand;

    @Mock
    private UpdateProfileCommand updateProfileCommand;

    @InjectMocks
    private ProfileController profileController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListProfiles() {
        when(getProfilesQuery.run()).thenReturn(profiles());

        profileController.all();

        verify(resourceBuilder).wrappedFrom(eq(profiles()), any(), eq(ProfileResource.class));
    }

    @Test
    public void shouldGetProfileById() {
        when(getProfileByIdQuery.run(eq(alanProfile().getId()))).thenReturn(alanProfile());

        ProfileResource profileResource = profileController.get(alanProfile().getId());

        assertThat(profileResource.getProfile(), is(alanProfile()));
    }

    @Test
    public void shouldCreateNewProfile() {
        when(createProfileCommand.run(createAlanProfileRequest())).thenReturn(alanProfile());

        ResponseEntity<Profile> entity = profileController.post(createAlanProfileRequest());

        assertThat(entity.getBody(), is(alanProfile()));
    }

    @Test
    public void shouldUpdateProfile() {
        when(updateProfileCommand.run(alanProfile().getId(), updateAlanProfileRequest())).thenReturn(updatedAlanProfile());

        ResponseEntity<Profile> entity = profileController.put(alanProfile().getId(), updateAlanProfileRequest());

        assertThat(entity.getBody(), is(updatedAlanProfile()));
    }

    @Test
    public void shouldDeleteProfile() {
        profileController.delete(alanProfile().getId());

        verify(deleteProfileCommand).run(alanProfile().getId());
    }
}