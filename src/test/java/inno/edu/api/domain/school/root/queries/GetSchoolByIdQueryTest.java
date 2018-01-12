package inno.edu.api.domain.school.root.queries;

import inno.edu.api.domain.school.root.exceptions.SchoolNotFoundException;
import inno.edu.api.domain.school.root.models.School;
import inno.edu.api.domain.school.root.repositories.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SchoolFactory.stanford;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetSchoolByIdQueryTest {
    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private GetSchoolByIdQuery getSchoolByIdQuery;

    @Test(expected = SchoolNotFoundException.class)
    public void shouldThrowExceptionIfSchoolDoesNotExist() {
        when(schoolRepository.findOne(stanford().getId())).thenReturn(null);

        getSchoolByIdQuery.run(stanford().getId());
    }

    @Test
    public void shouldReturnSchool() {
        when(schoolRepository.findOne(stanford().getId())).thenReturn(stanford());

        School school = getSchoolByIdQuery.run(stanford().getId());

        assertThat(school, is(stanford()));
    }
}