package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.ExperienceResource;
import inno.edu.api.controllers.resources.ProfileAssociationResource;
import inno.edu.api.controllers.resources.ProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.association.commands.ApproveProfileAssociationCommand;
import inno.edu.api.domain.profile.association.commands.AssociateProfileCommand;
import inno.edu.api.domain.profile.experience.commands.CreateExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.DeleteExperienceCommand;
import inno.edu.api.domain.profile.experience.commands.UpdateExperienceCommand;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.queries.GetExperienceByIdQuery;
import inno.edu.api.domain.profile.experience.queries.GetExperiencesByProfileIdQuery;
import inno.edu.api.domain.profile.root.commands.CreateProfileCommand;
import inno.edu.api.domain.profile.root.commands.DeleteProfileCommand;
import inno.edu.api.domain.profile.association.commands.RejectProfileAssociationCommand;
import inno.edu.api.domain.profile.root.commands.UpdateProfileCommand;
import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.association.queries.GetAssociationsByProfileIdQuery;
import inno.edu.api.domain.profile.root.queries.GetProfileByIdQuery;
import inno.edu.api.domain.profile.root.repositories.ProfileRepository;
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
import static inno.edu.api.support.ProfileFactory.approveRequest;
import static inno.edu.api.support.ProfileFactory.associations;
import static inno.edu.api.support.ProfileFactory.createAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.createFeiExperienceRequest;
import static inno.edu.api.support.ProfileFactory.feiExperience;
import static inno.edu.api.support.ProfileFactory.feiExperiences;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfile;
import static inno.edu.api.support.ProfileFactory.gustavoProfileAssociation;
import static inno.edu.api.support.ProfileFactory.gustavoToBerkeleyRequest;
import static inno.edu.api.support.ProfileFactory.profiles;
import static inno.edu.api.support.ProfileFactory.rejectRequest;
import static inno.edu.api.support.ProfileFactory.updateAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.updateFeiExperienceRequest;
import static inno.edu.api.support.ProfileFactory.updatedAlanProfile;
import static inno.edu.api.support.ProfileFactory.updatedFeiExperience;
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
    private GetExperienceByIdQuery getExperienceByIdQuery;

    @Mock
    private GetAssociationsByProfileIdQuery getAssociationsByProfileIdQuery;

    @Mock
    private GetExperiencesByProfileIdQuery getExperiencesByProfileIdQuery;

    @Mock
    private UpdateProfileCommand updateProfileCommand;

    @Mock
    private CreateProfileCommand createProfileCommand;

    @Mock
    private DeleteProfileCommand deleteProfileCommand;

    @Mock
    private AssociateProfileCommand associateProfileCommand;

    @Mock
    private ApproveProfileAssociationCommand approveProfileAssociationCommand;

    @Mock
    private RejectProfileAssociationCommand rejectProfileAssociationCommand;

    @Mock
    private CreateExperienceCommand createExperienceCommand;

    @Mock
    private UpdateExperienceCommand updateExperienceCommand;

    @Mock
    private DeleteExperienceCommand deleteExperienceCommand;

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

    @Test
    public void shouldAssociateProfileToSchool() {
        profileController.associate(gustavoProfile().getId(), gustavoToBerkeleyRequest());

        verify(associateProfileCommand).run(gustavoProfile().getId(), gustavoToBerkeleyRequest());
    }

    @Test
    public void shouldListAssociationsForProfile() {
        when(getAssociationsByProfileIdQuery.run(gustavoProfile().getId(), gustavoProfileAssociation().getStatus()))
                .thenReturn(associations());

        profileController.associations(gustavoProfile().getId(), gustavoProfileAssociation().getStatus());

        verify(resourceBuilder).wrappedFrom(eq(associations()), any(), eq(ProfileAssociationResource.class));
    }

    @Test
    public void shouldApproveAssociation() {
        profileController.approveAssociation(gustavoProfileAssociation().getId(), approveRequest());

        verify(approveProfileAssociationCommand).run(gustavoProfileAssociation().getId(), approveRequest());
    }

    @Test
    public void shouldRejectAssociation() {
        profileController.rejectAssociation(gustavoProfileAssociation().getId(), rejectRequest());

        verify(rejectProfileAssociationCommand).run(gustavoProfileAssociation().getId(), rejectRequest());
    }

    @Test
    public void shouldListExperiencesForProfile() {
        when(getExperiencesByProfileIdQuery.run(feiProfile().getId())).thenReturn(feiExperiences());

        profileController.experiences(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(feiExperiences()), any(), eq(ExperienceResource.class));
    }

    @Test
    public void shouldGetExperienceById() {
        when(getExperienceByIdQuery.run(eq(feiExperience().getId()))).thenReturn(feiExperience());

        ExperienceResource experienceResource = profileController.getExperience(feiExperience().getId());

        assertThat(experienceResource.getExperience(), is(feiExperience()));
    }

    @Test
    public void shouldCreateNewExperience() {
        when(createExperienceCommand.run(feiProfile().getId(), createFeiExperienceRequest())).thenReturn(feiExperience());

        ResponseEntity<Experience> entity = profileController.postExperience(feiProfile().getId(), createFeiExperienceRequest());

        assertThat(entity.getBody(), is(feiExperience()));
    }

    @Test
    public void shouldUpdateExperience() {
        when(updateExperienceCommand.run(feiExperience().getId(), updateFeiExperienceRequest())).thenReturn(updatedFeiExperience());

        ResponseEntity<Experience> entity = profileController.putExperience(feiExperience().getId(), updateFeiExperienceRequest());

        assertThat(entity.getBody(), is(updatedFeiExperience()));
    }

    @Test
    public void shouldDeleteExperience() {
        profileController.delete(feiExperience().getId());

        verify(deleteProfileCommand).run(feiExperience().getId());
    }
}