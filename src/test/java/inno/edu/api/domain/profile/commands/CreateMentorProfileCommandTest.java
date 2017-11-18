package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static inno.edu.api.support.UserFactory.feiProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateMentorProfileCommandTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @Mock
    private DeactivateMentorProfilesCommand deactivateMentorProfilesCommand;

    @InjectMocks
    private CreateMentorProfileCommand createMentorProfileCommand;

    @Test
    public void shouldCallRepositoryToSaveMentorProfile() {
        ArgumentCaptor<MentorProfile> argumentCaptor = forClass(MentorProfile.class);

        when(mentorProfileRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        MentorProfile mentorProfile = createMentorProfileCommand.run(feiProfile());

        assertThat(mentorProfile, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForMentorProfile() {
        ArgumentCaptor<MentorProfile> argumentCaptor = forClass(MentorProfile.class);

        when(mentorProfileRepository.save(argumentCaptor.capture())).thenReturn(feiProfile());

        createMentorProfileCommand.run(feiProfile());

        assertThat(argumentCaptor.getValue().getId(), not(feiProfile().getId()));
    }

    @Test
    public void shouldSetMentorProfileToCreated() {
        ArgumentCaptor<MentorProfile> argumentCaptor = forClass(MentorProfile.class);

        when(mentorProfileRepository.save(argumentCaptor.capture())).thenReturn(feiProfile());

        createMentorProfileCommand.run(feiProfile());

        assertThat(argumentCaptor.getValue().getStatus(), is(CREATED));
    }

    @Test
    public void shouldDeactivateOtherMentorProfiles() {
        createMentorProfileCommand.run(feiProfile());

        verify(deactivateMentorProfilesCommand).run(feiProfile().getMentorId());
    }
}