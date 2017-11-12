package inno.edu.api.domain.university.commands;

import inno.edu.api.domain.university.exceptions.UniversityNotFoundException;
import inno.edu.api.domain.university.models.University;
import inno.edu.api.domain.university.repositories.UniversityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.UniversityFactory.stanford;
import static inno.edu.api.support.UniversityFactory.updatedStanford;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUniversityCommandTest {
    @Mock
    private UniversityRepository universityRepository;

    @InjectMocks
    private UpdateUniversityCommand updateUniversityCommand;

    @Test
    public void shouldReturnUpdatedUniversity() {
        when(universityRepository.findOne(stanford().getId())).thenReturn(stanford());
        when(universityRepository.save(stanford())).thenReturn(updatedStanford());

        University university = updateUniversityCommand.run(stanford().getId(), stanford());

        assertThat(university, is(updatedStanford()));
    }

    @Test(expected = UniversityNotFoundException.class)
    public void shouldRaiseExceptionIfUniversityDoesNotExist() {
        when(universityRepository.findOne(stanford().getId())).thenThrow(new UniversityNotFoundException(stanford().getId()));

        updateUniversityCommand.run(randomUUID(), stanford());
    }
}