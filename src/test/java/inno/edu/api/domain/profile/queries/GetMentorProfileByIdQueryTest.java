package inno.edu.api.domain.profile.queries;

import inno.edu.api.domain.profile.exceptions.ProfileNotFoundException;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UserFactory.feiProfile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMentorProfileByIdQueryTest {
    @Mock
    private MentorProfileRepository profileRepository;

    @InjectMocks
    private GetMentorProfileByIdQuery getMentorProfileByIdQuery;

    @Test(expected = ProfileNotFoundException.class)
    public void shouldThrowExceptionIfMentorProfileDoesNotExist() {
        when(profileRepository.findOne(feiProfile().getId())).thenReturn(null);

        getMentorProfileByIdQuery.run(feiProfile().getId());
    }

    @Test
    public void shouldReturnMentorProfile() {
        when(profileRepository.findOne(feiProfile().getId())).thenReturn(feiProfile());

        MentorProfile profile = getMentorProfileByIdQuery.run(feiProfile().getId());

        assertThat(profile, is(feiProfile()));
    }
}