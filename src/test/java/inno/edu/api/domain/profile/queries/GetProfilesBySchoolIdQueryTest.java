package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.models.Profile;
import inno.edu.api.domain.profile.repositories.ProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.ProfileFactory.profiles;
import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetProfilesBySchoolIdQueryTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private SchoolExistsAssertion schoolExistsAssertion;

    @InjectMocks
    private GetProfilesBySchoolIdQuery getProfilesBySchoolIdQuery;

    @Test
    public void shouldGetSchoolMentorProfiles() {
        when(profileRepository.findBySchoolId(stanford().getId())).thenReturn(profiles());

        List<Profile> profiles = getProfilesBySchoolIdQuery.run(stanford().getId());

        assertThat(profiles, is(profiles()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getProfilesBySchoolIdQuery.run(stanford().getId());

        verify(schoolExistsAssertion).run(stanford().getId());
    }
}