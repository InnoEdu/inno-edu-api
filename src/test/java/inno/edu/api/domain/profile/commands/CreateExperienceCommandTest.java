package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.commands.mappers.CreateExperienceRequestMapper;
import inno.edu.api.domain.profile.models.Experience;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.createFeiExperienceRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.newFeiExperience;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateExperienceCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateExperienceRequestMapper createExperienceRequestMapper;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private ExperienceRepository experienceRepository;

    @InjectMocks
    private CreateExperienceCommand createExperienceCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createExperienceRequestMapper.toExperience(createFeiExperienceRequest()))
                .thenReturn(newFeiExperience(null));
    }

    @Test
    public void shouldSaveNewProfile() {
        Experience newExperience = newFeiExperience(uuidGeneratorService.generate());
        when(experienceRepository.save(newExperience)).thenReturn(newExperience);

        Experience savedProfile = createExperienceCommand.run(feiProfile().getId(), createFeiExperienceRequest());
        assertThat(savedProfile, is(newExperience));
    }

    @Test
    public void shouldRunAllAssertions() {
        createExperienceCommand.run(feiProfile().getId(), createFeiExperienceRequest());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}