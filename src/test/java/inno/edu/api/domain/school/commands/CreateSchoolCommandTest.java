package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateSchoolCommandTest {
    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private CreateSchoolCommand createSchoolCommand;

    @Test
    public void shouldCallRepositoryToSaveSchool() {
        ArgumentCaptor<School> argumentCaptor = forClass(School.class);

        when(schoolRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        School school = createSchoolCommand.run(stanford());

        assertThat(school, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForSchool() {
        ArgumentCaptor<School> argumentCaptor = forClass(School.class);

        when(schoolRepository.save(argumentCaptor.capture())).thenReturn(stanford());

        createSchoolCommand.run(stanford());

        assertThat(argumentCaptor.getValue().getId(), not(stanford().getId()));
    }
}