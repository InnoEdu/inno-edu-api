package inno.edu.api.controllers.profile;

import inno.edu.api.domain.profile.service.models.resources.ServiceResource;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import inno.edu.api.domain.profile.service.commands.CreateServiceCommand;
import inno.edu.api.domain.profile.service.commands.DeleteServiceCommand;
import inno.edu.api.domain.profile.service.commands.UpdateServiceCommand;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.queries.GetServiceByIdQuery;
import inno.edu.api.domain.profile.service.queries.GetServicesByProfileIdQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;

import static inno.edu.api.support.ProfileFactory.createFeiServiceRequest;
import static inno.edu.api.support.ProfileFactory.feiService;
import static inno.edu.api.support.ProfileFactory.feiServices;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.updateFeiServiceRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiService;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ServiceControllerTest {
    @Mock
    private ResourceBuilder resourceBuilder;

    @Mock
    private GetServiceByIdQuery getServiceByIdQuery;

    @Mock
    private GetServicesByProfileIdQuery getServicesByProfileIdQuery;

    @Mock
    private CreateServiceCommand createServiceCommand;

    @Mock
    private UpdateServiceCommand updateServiceCommand;

    @Mock
    private DeleteServiceCommand deleteServiceCommand;

    @InjectMocks
    private ServiceController serviceController;

    @Before
    public void setup() {
        setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void shouldListServicesForProfile() {
        when(getServicesByProfileIdQuery.run(feiProfile().getId())).thenReturn(feiServices());

        serviceController.all(feiProfile().getId());

        verify(resourceBuilder).wrappedFrom(eq(feiServices()), any(), eq(ServiceResource.class));
    }

    @Test
    public void shouldGetServiceById() {
        when(getServiceByIdQuery.run(eq(feiService().getId()))).thenReturn(feiService());

        ServiceResource serviceResource = serviceController.get(feiService().getId());

        assertThat(serviceResource.getService(), is(feiService()));
    }

    @Test
    public void shouldCreateNewService() {
        when(createServiceCommand.run(feiProfile().getId(), createFeiServiceRequest())).thenReturn(feiService());

        ResponseEntity<Service> entity = serviceController.post(feiProfile().getId(), createFeiServiceRequest());

        assertThat(entity.getBody(), is(feiService()));
    }

    @Test
    public void shouldUpdateService() {
        when(updateServiceCommand.run(feiService().getId(), updateFeiServiceRequest())).thenReturn(updatedFeiService());

        ResponseEntity<Service> entity = serviceController.put(feiService().getId(), updateFeiServiceRequest());

        assertThat(entity.getBody(), is(updatedFeiService()));
    }

    @Test
    public void shouldDeleteService() {
        serviceController.delete(feiService().getId());

        verify(deleteServiceCommand).run(feiService().getId());
    }
}