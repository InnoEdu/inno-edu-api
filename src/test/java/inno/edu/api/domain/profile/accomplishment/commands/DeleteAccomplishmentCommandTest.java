package inno.edu.api.domain.profile.accomplishment.commands;

import inno.edu.api.domain.profile.accomplishment.assertions.AccomplishmentExistsAssertion;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiAccomplishment;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAccomplishmentCommandTest {
    @Mock
    private AccomplishmentExistsAssertion accomplishmentExistsAssertion;

    @Mock
    private AccomplishmentRepository accomplishmentRepository;

    @InjectMocks
    private DeleteAccomplishmentCommand deleteAccomplishmentCommand;

    @Test
    public void shouldCallRepositoryToDeleteAccomplishment() {
        deleteAccomplishmentCommand.run(feiAccomplishment().getId());

        verify(accomplishmentRepository).delete(feiAccomplishment().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteAccomplishmentCommand.run(feiAccomplishment().getId());

        verify(accomplishmentExistsAssertion).run(feiAccomplishment().getId());
    }
}