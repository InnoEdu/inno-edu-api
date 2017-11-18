package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.MentorProfileResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.profile.commands.ApproveMentorProfileCommand;
import inno.edu.api.domain.profile.commands.CreateMentorProfileCommand;
import inno.edu.api.domain.profile.commands.UpdateMentorProfileCommand;
import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.UserFactory.feiProfile;
import static inno.edu.api.support.UserFactory.mentorProfiles;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class MentorProfileControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @Mock
    private CreateMentorProfileCommand createMentorProfileCommand;

    @Mock
    private UpdateMentorProfileCommand updateMentorProfileCommand;

    @Mock
    private ApproveMentorProfileCommand approveMentorProfileCommand;

    @InjectMocks
    private MentorProfileController mentorProfileController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetProfileById() {
        when(mentorProfileRepository.findOne(eq(feiProfile().getId()))).thenReturn(feiProfile());

        MentorProfileResource profileResource = mentorProfileController.get(feiProfile().getId());

        assertThat(profileResource.getMentorProfile(), is(feiProfile()));
    }

    @Test(expected = ProfileNotFoundException.class)
    public void shouldRaiseExceptionIfProfileNotFound() {
        when(mentorProfileRepository.findOne(any())).thenReturn(null);

        mentorProfileController.get(feiProfile().getId());
    }

    @Test
    public void shouldListProfiles() {
        when(mentorProfileRepository.findAll()).thenReturn(mentorProfiles());

        mentorProfileController.all();

        verify(resourceBuilder).from(eq(mentorProfiles()), any());
    }

    @Test
    public void shouldCreateNewProfiles() {
        ArgumentCaptor<MentorProfile> argumentCaptor = forClass(MentorProfile.class);
        when(createMentorProfileCommand.run(argumentCaptor.capture())).thenReturn(feiProfile());

        mentorProfileController.post(feiProfile());

        verify(createMentorProfileCommand).run(argumentCaptor.capture());
    }

    @Test
    public void shouldUpdateProfile() {
        when(updateMentorProfileCommand.run(feiProfile().getId(), feiProfile())).thenReturn(feiProfile());

        mentorProfileController.put(feiProfile().getId(), feiProfile());

        verify(updateMentorProfileCommand).run(feiProfile().getId(), feiProfile());
    }

    @Test
    public void shouldDeleteProfile() {
        when(mentorProfileRepository.exists(feiProfile().getId())).thenReturn(true);

        mentorProfileController.delete(feiProfile().getId());

        verify(mentorProfileRepository).delete(feiProfile().getId());
    }

    @Test
    public void shouldApproveProfile() {
        mentorProfileController.approve(feiProfile().getId());

        verify(approveMentorProfileCommand).run(feiProfile().getId());
    }
}