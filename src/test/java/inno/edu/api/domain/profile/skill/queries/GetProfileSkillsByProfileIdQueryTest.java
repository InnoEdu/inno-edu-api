package inno.edu.api.domain.profile.skill.queries;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.skill.repositories.ProfileSkillRepository;
import inno.edu.api.domain.skill.models.Skill;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.feiProfileSkills;
import static inno.edu.api.support.SkillFactory.skills;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetProfileSkillsByProfileIdQueryTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private ProfileSkillRepository profileSkillRepository;

    @InjectMocks
    private GetProfileSkillsByProfileIdQuery getProfileSkillsByProfileIdQuery;

    @Before
    public void setUp() {
        when(profileSkillRepository.findByProfileId(feiProfile().getId()))
                .thenReturn(feiProfileSkills());
    }

    @Test
    public void shouldReturnProfileSkills() {
        List<Skill> actualSkills = getProfileSkillsByProfileIdQuery.run(feiProfile().getId());

        assertThat(actualSkills, is(skills()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getProfileSkillsByProfileIdQuery.run(feiProfile().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}