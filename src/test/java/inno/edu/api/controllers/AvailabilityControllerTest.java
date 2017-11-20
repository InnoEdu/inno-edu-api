package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.availability.commands.CreateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.DeleteAvailabilityCommand;
import inno.edu.api.domain.availability.commands.UpdateAvailabilityCommand;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.queries.GetAvailabilityByIdQuery;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.AvailabilityFactory.allAvailability;
import static inno.edu.api.support.AvailabilityFactory.availability;
import static inno.edu.api.support.AvailabilityFactory.updatedAvailability;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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

    @Mock
    private DeleteAvailabilityCommand deleteAvailabilityCommand;

    @Mock
    private GetAvailabilityByIdQuery getAvailabilityByIdQuery;

    @InjectMocks
    private AvailabilityController availabilityController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldGetAvailabilityUsingId() {
        when(getAvailabilityByIdQuery.run(eq(availability().getId()))).thenReturn(availability());

        AvailabilityResource availabilityResource = availabilityController.get(availability().getId());

        assertThat(availabilityResource.getAvailability(), is(availability()));
    }

    @Test
    public void shouldListAllAvailability() {
        when(availabilityRepository.findAll()).thenReturn(allAvailability());

        availabilityController.all();

        verify(resourceBuilder).from(eq(allAvailability()), any());
    }

    @Test
    public void shouldCreateNewAvailability() {
        when(createAvailabilityCommand.run(availability())).thenReturn(availability());

        ResponseEntity<Availability> entity = availabilityController.post(availability());

        assertThat(entity.getBody(), is(availability()));
    }

    @Test
    public void shouldUpdateAvailability() {
        when(updateAvailabilityCommand.run(availability().getId(), updatedAvailability())).thenReturn(updatedAvailability());

        ResponseEntity<Availability> entity = availabilityController.put(availability().getId(), updatedAvailability());

        assertThat(entity.getBody(), is(updatedAvailability()));
    }

    @Test
    public void shouldDeleteAvailability() {
        availabilityController.delete(availability().getId());

        verify(deleteAvailabilityCommand).run(availability().getId());
    }
}