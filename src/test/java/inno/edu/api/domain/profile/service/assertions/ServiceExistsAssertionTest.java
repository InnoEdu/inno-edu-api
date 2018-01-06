package inno.edu.api.domain.profile.service.assertions;

import inno.edu.api.domain.profile.service.exceptions.ServiceNotFoundException;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiService;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceExistsAssertionTest {
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceExistsAssertion serviceExistsAssertion;

    @Test(expected = ServiceNotFoundException.class)
    public void shouldThrowExceptionIfServiceDoesNotExist() {
        when(serviceRepository.exists(feiService().getId())).thenReturn(false);

        serviceExistsAssertion.run(feiService().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfServiceExists() {
        when(serviceRepository.exists(feiService().getId())).thenReturn(true);

        serviceExistsAssertion.run(feiService().getId());
    }
}