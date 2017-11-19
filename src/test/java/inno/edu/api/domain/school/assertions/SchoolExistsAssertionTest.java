package inno.edu.api.domain.school.assertions;

import inno.edu.api.domain.school.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SchoolFactory.stanford;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchoolExistsAssertionTest {
    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SchoolExistsAssertion schoolExistsAssertion;

    @Test(expected = SchoolNotFoundException.class)
    public void shouldThrowExceptionIfSchoolDoesNotExist() {
        when(schoolRepository.exists(stanford().getId())).thenReturn(false);

        schoolExistsAssertion.run(stanford().getId());
    }

    @Test
    public void shouldNotThrowExceptionIfSchoolExists() {
        when(schoolRepository.exists(stanford().getId())).thenReturn(true);

        schoolExistsAssertion.run(stanford().getId());
    }
}