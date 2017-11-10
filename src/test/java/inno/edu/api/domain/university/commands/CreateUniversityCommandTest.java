package inno.edu.api.domain.university.commands;

import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.factories.UniversityFactory.stanford;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateUniversityCommandTest {
    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private CreateUniversityCommand createUniversityCommand;

    @Test
    public void shouldCallRepositoryToSaveUniversity() {
        ArgumentCaptor<University> argumentCaptor = forClass(University.class);

        when(universityRepository.save(argumentCaptor.capture()))
                .thenAnswer((invocation -> invocation.getArguments()[0]));

        University university = createUniversityCommand.run(stanford());

        assertThat(university, is(argumentCaptor.getValue()));
    }

    @Test
    public void shouldGenerateNewIdForUniversity() {
        ArgumentCaptor<University> argumentCaptor = forClass(University.class);

        when(universityRepository.save(argumentCaptor.capture())).thenReturn(stanford());

        createUniversityCommand.run(stanford());

        assertThat(argumentCaptor.getValue().getId(), not(stanford().getId()));
    }
}