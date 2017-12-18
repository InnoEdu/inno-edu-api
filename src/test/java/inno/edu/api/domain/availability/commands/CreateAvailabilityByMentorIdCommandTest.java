package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.commands.mappers.CreateAvailabilityByMentorIdRequestMapper;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.queries.GetMentorActiveProfileByUserIdQuery;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AvailabilityFactory.createAvailabilityByMentorRequest;
import static inno.edu.api.support.AvailabilityFactory.newAvailability;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.UserFactory.fei;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAvailabilityByMentorIdCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateAvailabilityByMentorIdRequestMapper createAvailabilityByMentorIdRequestMapper;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private GetMentorActiveProfileByUserIdQuery getMentorActiveProfileByUserIdQuery;

    @InjectMocks
    private CreateAvailabilityByMentorIdCommand createAvailabilityByMentorIdCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());
        when(getMentorActiveProfileByUserIdQuery.run(fei().getId())).thenReturn(feiProfile());

        when(createAvailabilityByMentorIdRequestMapper.toAvailability(createAvailabilityByMentorRequest()))
                .thenReturn(newAvailability(null, null));
    }

    @Test
    public void shouldSaveAvailabilityForMentor() {
        Availability newAvailability = newAvailability(uuidGeneratorService.generate(), feiProfile().getId());

        when(availabilityRepository.save(newAvailability)).thenReturn(newAvailability);

        Availability savedAvailability = createAvailabilityByMentorIdCommand.run(fei().getId(), createAvailabilityByMentorRequest());
        assertThat(savedAvailability, is(newAvailability));
    }
}