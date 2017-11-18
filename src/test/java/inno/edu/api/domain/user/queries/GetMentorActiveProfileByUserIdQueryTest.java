package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;
import static inno.edu.api.support.UserFactory.fei;
import static inno.edu.api.support.UserFactory.feiProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMentorActiveProfileByUserIdQueryTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;

    @Test
    public void shouldGetMentorProfile() {
        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), ACTIVE)).thenReturn(feiProfile());

        MentorProfile mentorProfile = getMentorActiveProfileByUserIdQuery.run(fei().getId());

        assertThat(mentorProfile, is(feiProfile()));
    }

    @Test(expected = UserProfileNotFoundException.class)
    public void shouldRaiseExceptionIsProfileNotFound() {
        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), ACTIVE)).thenReturn(null);

        getMentorActiveProfileByUserIdQuery.run(fei().getId());
    }
}