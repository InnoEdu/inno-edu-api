package inno.edu.api.domain.profile.service.commands;

import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.domain.profile.service.models.dtos.mappers.CreateServiceRequestMapper;
import inno.edu.api.domain.profile.service.models.Service;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.createFeiServiceRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.newFeiService;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateServiceCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateServiceRequestMapper createServiceRequestMapper;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private CreateServiceCommand createServiceCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createServiceRequestMapper.toService(createFeiServiceRequest()))
                .thenReturn(newFeiService(null));
    }

    @Test
    public void shouldSaveNewProfile() {
        Service newService = newFeiService(uuidGeneratorService.generate());
        when(serviceRepository.save(newService)).thenReturn(newService);

        Service savedProfile = createServiceCommand.run(feiProfile().getId(), createFeiServiceRequest());
        assertThat(savedProfile, is(newService));
    }

    @Test
    public void shouldRunAllAssertions() {
        createServiceCommand.run(feiProfile().getId(), createFeiServiceRequest());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}