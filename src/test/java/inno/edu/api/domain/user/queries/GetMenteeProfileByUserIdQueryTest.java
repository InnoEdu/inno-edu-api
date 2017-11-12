package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.user.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.user.models.MenteeProfile;
import inno.edu.api.domain.user.repositories.MenteeProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.factories.UserFactory.alan;
import static inno.edu.api.factories.UserFactory.alanProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMenteeProfileByUserIdQueryTest {
    @Mock
    private MenteeProfileRepository menteeProfileRepository;

    @InjectMocks
    private GetMenteeProfileByUserIdQuery getMenteeProfileByUserIdQuery;

    @Test
    public void shouldGetMenteeProfile() {
        when(menteeProfileRepository.findOneByMenteeId(alan().getId())).thenReturn(alanProfile());

        MenteeProfile mentorProfile = getMenteeProfileByUserIdQuery.run(alan().getId());

        assertThat(mentorProfile, is(alanProfile()));
    }

    @Test(expected = UserProfileNotFoundException.class)
    public void shouldRaiseExceptionIsProfileNotFound() {
        when(menteeProfileRepository.findOneByMenteeId(alan().getId())).thenReturn(null);

        getMenteeProfileByUserIdQuery.run(alan().getId());
    }

}