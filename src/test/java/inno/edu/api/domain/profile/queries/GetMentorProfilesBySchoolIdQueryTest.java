package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.domain.profile.models.ProfileStatus.ACTIVE;
import static inno.edu.api.support.ProfileFactory.mentorProfiles;
import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMentorProfilesBySchoolIdQueryTest {
    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @Mock
    private SchoolExistsAssertion schoolExistsAssertion;

    @InjectMocks
    private GetMentorProfilesBySchoolIdQuery getMentorProfilesBySchoolIdQuery;

    @Test
    public void shouldGetSchoolMentorProfiles() {
        when(mentorProfileRepository.findBySchoolIdAndStatus(stanford().getId(), ACTIVE)).thenReturn(mentorProfiles());

        List<MentorProfile> mentorProfiles = getMentorProfilesBySchoolIdQuery.run(stanford().getId());

        assertThat(mentorProfiles, is(mentorProfiles()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getMentorProfilesBySchoolIdQuery.run(stanford().getId());

        verify(schoolExistsAssertion).run(stanford().getId());
    }
}