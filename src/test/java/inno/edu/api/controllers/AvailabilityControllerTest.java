package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.availability.commands.CreateAvailabilityCommand;
import inno.edu.api.domain.availability.commands.DeleteAvailabilityCommand;
import inno.edu.api.domain.availability.commands.UpdateAvailabilityCommand;
import inno.edu.api.domain.availability.models.Availability;
import inno.edu.api.domain.availability.queries.GetAvailabilityByIdQuery;
import inno.edu.api.domain.availability.queries.GetAvailabilityByProfileId;
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
import static inno.edu.api.support.AvailabilityFactory.createAvailabilityRequest;
import static inno.edu.api.support.AvailabilityFactory.feiAvailability;
import static inno.edu.api.support.AvailabilityFactory.updateAvailabilityRequest;
import static inno.edu.api.support.AvailabilityFactory.updatedAvailability;
import static inno.edu.api.support.ProfileFactory.feiProfile;
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

    @Mock
    private GetAvailabilityByProfileId getAvailabilityByProfileId;

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

        verify(resourceBuilder).wrappedFrom(eq(allAvailability()), any(), eq(AvailabilityResource.class));
    }

    @Test
    public void shouldCreateNewAvailability() {
        when(createAvailabilityCommand.run(createAvailabilityRequest())).thenReturn(availability());

        ResponseEntity<Availability> entity = availabilityController.post(createAvailabilityRequest());

        assertThat(entity.getBody(), is(availability()));
    }

    @Test
    public void shouldUpdateAvailability() {
        when(updateAvailabilityCommand.run(availability().getId(), updateAvailabilityRequest())).thenReturn(updatedAvailability());

        ResponseEntity<Availability> entity = availabilityController.put(availability().getId(), updateAvailabilityRequest());

        assertThat(entity.getBody(), is(updatedAvailability()));
    }

    @Test
    public void shouldDeleteAvailability() {
        availabilityController.delete(availability().getId());

        verify(deleteAvailabilityCommand).run(availability().getId());
    }

    @Test
    public void shouldListAvailabilityByMentor() {
        when(getAvailabilityByProfileId.run(feiProfile().getId())).thenReturn(feiAvailability());

        availabilityController.allByProfile(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(feiAvailability()), any(), eq(AvailabilityResource.class));
    }

}