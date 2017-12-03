package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.UserProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.mentorProfiles;
import static inno.edu.api.support.UserFactory.fei;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMentorProfileByUserIdQueryTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private GetMentorProfileByUserIdQuery getMentorProfileByUserIdQuery;

    @Test
    public void shouldGetMentorProfile() {
        when(mentorProfileRepository.findByMentorId(fei().getId())).thenReturn(mentorProfiles());

        MentorProfile mentorProfile = getMentorProfileByUserIdQuery.run(fei().getId());

        assertThat(mentorProfile, is(feiProfile()));
    }

    @Test(expected = UserProfileNotFoundException.class)
    public void shouldRaiseExceptionIsProfileNotFound() {
        when(mentorProfileRepository.findByMentorId(fei().getId())).thenReturn(emptyList());

        getMentorProfileByUserIdQuery.run(fei().getId());
    }

    public static void main(String[] args) {
        Integer a = new Integer(3);
        Integer b = new Integer(3);
        System.out.println(a == b);
    }
}