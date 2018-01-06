package inno.edu.api.domain.profile.service.commands;

import inno.edu.api.domain.profile.service.commands.mappers.UpdateServiceRequestMapper;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.queries.GetServiceByIdQuery;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiService;
import static inno.edu.api.support.ProfileFactory.updateFeiServiceRequest;
import static inno.edu.api.support.ProfileFactory.updatedFeiService;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateServiceCommandTest {
    @Mock
    private UpdateServiceRequestMapper updateServiceRequestMapper;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private GetServiceByIdQuery getServiceByIdQuery;

    @InjectMocks
    private UpdateServiceCommand updateServiceCommand;

    @Test
    public void shouldReturnUpdatedService() {
        when(getServiceByIdQuery.run(feiService().getId())).thenReturn(feiService());
        when(serviceRepository.save(feiService())).thenReturn(updatedFeiService());

        Service Service = updateServiceCommand.run(feiService().getId(), updateFeiServiceRequest());

        verify(updateServiceRequestMapper).setService(updateFeiServiceRequest(), feiService());

        assertThat(Service, is(updatedFeiService()));
    }
}