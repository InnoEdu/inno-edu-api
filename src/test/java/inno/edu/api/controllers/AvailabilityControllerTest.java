package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.availability.commands.CreateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.UpdateAvailabilityCommand;
import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.factories.AvailabilityFactory.allAvailability;
import static inno.edu.api.factories.AvailabilityFactory.availability;
import static inno.edu.api.factories.AvailabilityFactory.feiAvailability;
import static inno.edu.api.factories.UniversityFactory.stanford;
import static inno.edu.api.factories.UserFactory.fei;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class AvailabilityControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private CreateAvailabilityCommand createAvailabilityCommand;

    @Mock
    private UpdateAvailabilityCommand updateAvailabilityCommand;

    @InjectMocks
    private AvailabilityController availabilityController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetAvailabilityUsingId() {
        when(availabilityRepository.findOne(eq(availability().getId()))).thenReturn(availability());

        AvailabilityResource availabilityResource = availabilityController.get(availability().getId());

        assertThat(availabilityResource.getAvailability(), is(availability()));
    }

    @Test(expected = AvailabilityNotFoundException.class)
    public void shouldRaiseExceptionIfAvailabilityNotFound() {
        when(availabilityRepository.findOne(any())).thenReturn(null);

        availabilityController.get(availability().getId());
    }

    @Test
    public void shouldListAllAvailability() {
        when(availabilityRepository.findAll()).thenReturn(allAvailability());

        availabilityController.all();

        verify(resourceBuilder).from(eq(allAvailability()), any());
    }

    @Test
    public void shouldListAllAvailabilityByMentor() {
        when(availabilityRepository.findByMentorId(fei().getId())).thenReturn(feiAvailability());

        availabilityController.allByMentor(fei().getId());

        verify(resourceBuilder).from(eq(feiAvailability()), any());
    }

    @Test
    public void shouldListAllAvailabilityByUniversity() {
        when(availabilityRepository.findByUniversityId(stanford().getId())).thenReturn(feiAvailability());

        availabilityController.allByUniversity(stanford().getId());

        verify(resourceBuilder).from(eq(feiAvailability()), any());
    }

    @Test
    public void shouldCreateNewAvailability() {
        ArgumentCaptor<Availability> argumentCaptor = forClass(Availability.class);
        when(createAvailabilityCommand.run(argumentCaptor.capture())).thenReturn(availability());

        availabilityController.post(availability());

        verify(createAvailabilityCommand).run(argumentCaptor.capture());
    }

    @Test
    public void shouldUpdateAvailability() {
        when(updateAvailabilityCommand.run(availability().getId(), availability())).thenReturn(availability());

        availabilityController.put(availability().getId(), availability());

        verify(updateAvailabilityCommand).run(availability().getId(), availability());
    }

    @Test
    public void shouldUDeleteAvailability() {
        when(availabilityRepository.exists(availability().getId())).thenReturn(true);

        availabilityController.delete(availability().getId());

        verify(availabilityRepository).delete(availability().getId());
    }
}