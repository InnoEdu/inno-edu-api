package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMentorActiveProfileByUserIdQueryTest {

    @Mock
    private UserIsMentorAssertion userIsMentorAssertion;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;

    @Before
    public void setUp() {
        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), ACTIVE)).thenReturn(feiProfile());
    }

    @Test
    public void shouldGetMentorProfile() {
        MentorProfile mentorProfile = getMentorActiveProfileByUserIdQuery.run(fei().getId());

        assertThat(mentorProfile, is(feiProfile()));
    }

    @Test(expected = UserProfileNotFoundException.class)
    public void shouldRaiseExceptionIsProfileNotFound() {
        when(mentorProfileRepository.findOneByMentorIdAndStatus(fei().getId(), ACTIVE)).thenReturn(null);

        getMentorActiveProfileByUserIdQuery.run(fei().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        getMentorActiveProfileByUserIdQuery.run(fei().getId());

        verify(userIsMentorAssertion).run(fei().getId());
    }
}