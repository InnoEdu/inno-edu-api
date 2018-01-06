package inno.edu.api.domain.profile.service.commands;

import inno.edu.api.domain.profile.service.assertions.ServiceExistsAssertion;
import inno.edu.api.domain.profile.service.repositories.ServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiService;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteServiceCommandTest {
    @Mock
    private ServiceExistsAssertion interestExistsAssertion;

    @Mock
    private ServiceRepository interestRepository;

    @InjectMocks
    private DeleteServiceCommand deleteServiceCommand;

    @Test
    public void shouldCallRepositoryToDeleteService() {
        deleteServiceCommand.run(feiService().getId());

        verify(interestRepository).delete(feiService().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteServiceCommand.run(feiService().getId());

        verify(interestExistsAssertion).run(feiService().getId());
    }
}