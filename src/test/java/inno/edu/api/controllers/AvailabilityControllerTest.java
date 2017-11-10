package inno.edu.api.controllers;

import inno.edu.api.controllers.resources.AvailabilityResource;
import inno.edu.api.controllers.resources.ResourceBuilder;
import inno.edu.api.domain.availability.exceptions.AvailabilityNotFoundException;
import inno.edu.api.domain.availability.repositories.AvailabilityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.factories.AvailabilityFactory.allAvailability;
import static inno.edu.api.factories.AvailabilityFactory.availability;
import static inno.edu.api.factories.AvailabilityFactory.availabilityResources;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class AvailabilityControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private AvailabilityRepository availabilityRepository;

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
        when(resourceBuilder.fromResources(anyListOf(AvailabilityResource.class))).thenReturn(availabilityResources());

        Resources<AvailabilityResource> allResources = availabilityController.all();

        assertThat(allResources, is(availabilityResources()));
    }

}