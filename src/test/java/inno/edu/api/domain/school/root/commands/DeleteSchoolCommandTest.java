package inno.edu.api.domain.school.root.commands;

import inno.edu.api.domain.school.root.assertions.SchoolExistsAssertion;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SchoolFactory.stanford;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteSchoolCommandTest {
    @Mock
    private SchoolExistsAssertion schoolExistsAssertion;

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private DeleteSchoolCommand deleteSchoolCommand;

    @Test
    public void shouldCallRepositoryToDeleteSchool() {
        deleteSchoolCommand.run(stanford().getId());

        verify(schoolRepository).delete(stanford().getId());
    }

    @Test
    public void shouldRunAllAssertions() {
        deleteSchoolCommand.run(stanford().getId());

        verify(schoolExistsAssertion).run(stanford().getId());
    }
}