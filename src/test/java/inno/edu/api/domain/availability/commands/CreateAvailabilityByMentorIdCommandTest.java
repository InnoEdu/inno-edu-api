package inno.edu.api.domain.availability.commands;

import inno.edu.api.domain.availability.commands.mappers.CreateAvailabilityByMentorIdRequestMapper;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import inno.edu.api.domain.profile.queries.GetMentorActiveProfileByUserIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.AvailabilityFactory.availability;
import static inno.edu.api.support.AvailabilityFactory.createAvailabilityByMentorRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.UserFactory.fei;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateAvailabilityByMentorIdCommandTest {
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
        Availability emptyAvailability = availability().toBuilder()
                .id(null)
                .mentorProfileId(null)
                .build();

        when(getMentorActiveProfileByUserIdQuery.run(fei().getId())).thenReturn(feiProfile());

        when(createAvailabilityByMentorIdRequestMapper.toAvailability(createAvailabilityByMentorRequest()))
                .thenReturn(emptyAvailability);
    }

    @Test
    public void shouldCallRepositoryToSaveAvailabilityWithCorrectMentorProfileId() {
        ArgumentCaptor<Availability> argumentCaptor = forClass(Availability.class);

        when(availabilityRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        Availability availability = createAvailabilityByMentorIdCommand.run(fei().getId(), createAvailabilityByMentorRequest());

        assertThat(availability, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerAateNewIdForAvailability() {
        ArgumentCaptor<Availability> argumentCaptor = forClass(Availability.class);

        when(availabilityRepository.save(argumentCaptor.capture())).thenReturn(availability());

        createAvailabilityByMentorIdCommand.run(fei().getId(), createAvailabilityByMentorRequest());

        assertThat(argumentCaptor.getValue().getId(), not(availability().getId()));
    }
}