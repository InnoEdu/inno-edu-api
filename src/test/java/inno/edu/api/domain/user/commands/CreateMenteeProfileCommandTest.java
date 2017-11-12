package inno.edu.api.domain.user.commands;

import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.repositories.MenteeProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.factories.UserFactory.alanProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateMenteeProfileCommandTest {
    @Mock
    private MenteeProfileRepository menteeProfileRepository;

    @InjectMocks
    private CreateMenteeProfileCommand createMenteeProfileCommand;

    @Test
    public void shouldCallRepositoryToSaveMenteeProfile() {
        ArgumentCaptor<MenteeProfile> argumentCaptor = forClass(MenteeProfile.class);

        when(menteeProfileRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        MenteeProfile menteeProfile = createMenteeProfileCommand.run(alanProfile());

        assertThat(menteeProfile, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForMenteeProfile() {
        ArgumentCaptor<MenteeProfile> argumentCaptor = forClass(MenteeProfile.class);

        when(menteeProfileRepository.save(argumentCaptor.capture())).thenReturn(alanProfile());

        createMenteeProfileCommand.run(alanProfile());

        assertThat(argumentCaptor.getValue().getId(), not(alanProfile().getId()));
    }
}