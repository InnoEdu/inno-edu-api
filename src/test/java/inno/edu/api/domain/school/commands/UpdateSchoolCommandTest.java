package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SchoolFactory.stanford;
import static inno.edu.api.support.SchoolFactory.updatedStanford;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateSchoolCommandTest {
    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private UpdateSchoolCommand updateSchoolCommand;

    @Test
    public void shouldReturnUpdatedSchool() {
        when(schoolRepository.findOne(stanford().getId())).thenReturn(stanford());
        when(schoolRepository.save(stanford())).thenReturn(updatedStanford());

        School school = updateSchoolCommand.run(stanford().getId(), stanford());

        assertThat(school, is(updatedStanford()));
    }

    @Test(expected = SchoolNotFoundException.class)
    public void shouldRaiseExceptionIfSchoolDoesNotExist() {
        when(schoolRepository.findOne(stanford().getId())).thenThrow(new SchoolNotFoundException(stanford().getId()));

        updateSchoolCommand.run(randomUUID(), stanford());
    }
}