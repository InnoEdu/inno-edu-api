package inno.edu.api.domain.profile.skill.commands;

import inno.edu.api.domain.skill.commands.CreateSkillCommand;
import inno.edu.api.domain.skill.commands.dtos.CreateSkillRequest;
import inno.edu.api.domain.profile.skill.models.ProfileSkill;
import inno.edu.api.domain.profile.skill.repositories.ProfileSkillRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SkillFactory.skill;
import static inno.edu.api.support.SkillFactory.createSkillRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateProfileSkillCommandTest {
    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private CreateSkillCommand createSkillCommand;

    @Mock
    private ProfileSkillRepository profileSkillRepository;

    @InjectMocks
    private CreateProfileSkillCommand createProfileSkillCommand;

    @Before
    public void setUp() {
        when(createSkillCommand.run(any())).thenReturn(skill());
    }

    @Test
    public void shouldCreateSkill() {
        CreateSkillRequest skillRequest = createSkillRequest();

        createProfileSkillCommand.run(feiProfile().getId(), skillRequest);

        verify(createSkillCommand).run(skillRequest);
    }

    @Test
    public void shouldCreateProfileSkill() {
        CreateSkillRequest skillRequest = createSkillRequest();

        createProfileSkillCommand.run(feiProfile().getId(), skillRequest);

        ArgumentCaptor<ProfileSkill> captor = ArgumentCaptor.forClass(ProfileSkill.class);

        verify(profileSkillRepository).save(captor.capture());

        assertThat(captor.getValue().getProfileId(), is(feiProfile().getId()));
        assertThat(captor.getValue().getSkillId(), is(skill().getId()));
    }

    @Test
    public void shouldRunAllAssertions() {
        createProfileSkillCommand.run(feiProfile().getId(), createSkillRequest());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}