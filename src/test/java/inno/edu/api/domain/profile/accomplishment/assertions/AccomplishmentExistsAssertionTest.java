package inno.edu.api.domain.profile.accomplishment.assertions;

import inno.edu.api.domain.profile.accomplishment.exceptions.AccomplishmentNotFoundException;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.feiAccomplishment;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccomplishmentExistsAssertionTest {
    @Mock
    private AccomplishmentRepository accomplishmentRepository;

    @InjectMocks
    private AccomplishmentExistsAssertion accomplishmentExistsAssertion;

    @Test(expected = AccomplishmentNotFoundException.class)
    public void shouldThrowExceptionIfAccomplishmentDoesNotExist() {
        when(accomplishmentRepository.exists(feiAccomplishment().getId())).thenReturn(false);

        accomplishmentExistsAssertion.run(feiAccomplishment().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfAccomplishmentExists() {
        when(accomplishmentRepository.exists(feiAccomplishment().getId())).thenReturn(true);

        accomplishmentExistsAssertion.run(feiAccomplishment().getId());
    }
}