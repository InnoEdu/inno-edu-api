package inno.edu.api.domain.profile.service.queries;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.feiServices;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetServicesByProfileIdQueryTest {
    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @InjectMocks
    private GetServicesByProfileIdQuery getServicesByProfileIdQuery;

    @Test
    public void shouldGetServicesByProfile() {
        when(serviceRepository.findByProfileId(feiProfile().getId())).thenReturn(feiServices());

        List<Service> services = getServicesByProfileIdQuery.run(feiProfile().getId());

        assertThat(services, is(feiServices()));
    }

    @Test
    public void shouldRunAllAssertions() {
        getServicesByProfileIdQuery.run(feiProfile().getId());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}