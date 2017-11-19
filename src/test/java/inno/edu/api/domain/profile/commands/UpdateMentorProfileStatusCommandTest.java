package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.models.ProfileStatus;
import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMentorProfileStatusCommandTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @Mock
    private GetMentorProfileByIdQuery getMentorProfileByIdQuery;

    @InjectMocks
    private UpdateMentorProfileStatusCommand updateMentorProfileStatusCommand;

    @Test
    public void shouldApproveMentorProfile() {
        MentorProfile inactiveProfile = feiProfile()
                .toBuilder()
                .status(ProfileStatus.CREATED)
                .build();

        when(getMentorProfileByIdQuery.run(feiProfile().getId())).thenReturn(inactiveProfile);

        updateMentorProfileStatusCommand.run(feiProfile().getId(), ProfileStatus.ACTIVE);

        verify(mentorProfileRepository).save(feiProfile());
    }
}