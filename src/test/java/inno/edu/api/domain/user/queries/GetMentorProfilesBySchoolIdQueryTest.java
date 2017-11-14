package inno.edu.api.domain.user.queries;

import inno.edu.api.domain.user.models.MentorProfile;
import inno.edu.api.domain.user.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.domain.user.models.ProfileStatus.ACTIVE;
import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.UserFactory.mentorProfiles;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMentorProfilesBySchoolIdQueryTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @InjectMocks
    private GetMentorProfilesBySchoolIdQuery getMentorProfilesBySchoolIdQuery;

    @Test
    public void shouldGetSchoolMentorProfiles() {
        when(mentorProfileRepository.findBySchoolIdAndStatus(stanford().getId(), ACTIVE)).thenReturn(mentorProfiles());

        List<MentorProfile> mentorProfiles = getMentorProfilesBySchoolIdQuery.run(stanford().getId());

        assertThat(mentorProfiles, is(mentorProfiles()));
    }
}