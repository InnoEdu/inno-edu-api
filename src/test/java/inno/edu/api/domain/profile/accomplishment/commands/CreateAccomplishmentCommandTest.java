package inno.edu.api.domain.profile.accomplishment.commands;

import inno.edu.api.domain.profile.accomplishment.models.mappers.CreateAccomplishmentRequestMapper;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.createFeiAccomplishmentRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.newFeiAccomplishment;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccomplishmentCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateAccomplishmentRequestMapper createAccomplishmentRequestMapper;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private AccomplishmentRepository accomplishmentRepository;

    @InjectMocks
    private CreateAccomplishmentCommand createAccomplishmentCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createAccomplishmentRequestMapper.toAccomplishment(createFeiAccomplishmentRequest()))
                .thenReturn(newFeiAccomplishment(null));
    }

    @Test
    public void shouldSaveNewProfile() {
        Accomplishment newAccomplishment = newFeiAccomplishment(uuidGeneratorService.generate());
        when(accomplishmentRepository.save(newAccomplishment)).thenReturn(newAccomplishment);

        Accomplishment savedProfile = createAccomplishmentCommand.run(feiProfile().getId(), createFeiAccomplishmentRequest());
        assertThat(savedProfile, is(newAccomplishment));
    }

    @Test
    public void shouldRunAllAssertions() {
        createAccomplishmentCommand.run(feiProfile().getId(), createFeiAccomplishmentRequest());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}