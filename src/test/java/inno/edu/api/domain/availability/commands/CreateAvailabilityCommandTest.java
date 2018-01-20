package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.models.dtos.mappers.CreateAvailabilityRequestMapper;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AvailabilityFactory.availability;
import static inno.edu.api.support.AvailabilityFactory.createAvailabilityRequest;
import static inno.edu.api.support.AvailabilityFactory.newAvailability;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAvailabilityCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateAvailabilityRequestMapper createAvailabilityRequestMapper;
    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @InjectMocks
    private CreateAvailabilityCommand createAvailabilityCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createAvailabilityRequestMapper.toAvailability(createAvailabilityRequest()))
                .thenReturn(newAvailability(null, feiProfile().getId()));
    }

    @Test
    public void shouldCallRepositoryToSaveAvailability() {
        Availability availability = newAvailability(uuidGeneratorService.generate(), feiProfile().getId());
        when(availabilityRepository.save(availability)).thenReturn(availability);

        Availability savedAvailability = createAvailabilityCommand.run(createAvailabilityRequest());
        assertThat(savedAvailability, is(availability));
    }

    @Test
    public void shouldRunAllAssertions() {
        createAvailabilityCommand.run(createAvailabilityRequest());

        verify(profileExistsAssertion).run(availability().getMentorProfileId());
    }
}