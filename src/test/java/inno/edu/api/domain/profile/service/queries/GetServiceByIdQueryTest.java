package inno.edu.api.domain.profile.service.queries;

import inno.edu.api.domain.profile.service.exceptions.ServiceNotFoundException;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiService;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetServiceByIdQueryTest {
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private GetServiceByIdQuery getServiceByIdQuery;

    @Test(expected = ServiceNotFoundException.class)
    public void shouldThrowExceptionIfServiceDoesNotExist() {
        when(serviceRepository.findOne(feiService().getId())).thenReturn(null);

        getServiceByIdQuery.run(feiService().getId());
    }

    @Test
    public void shouldReturnService() {
        when(serviceRepository.findOne(feiService().getId())).thenReturn(feiService());

        Service service = getServiceByIdQuery.run(feiService().getId());

        assertThat(service, is(feiService()));
    }
}