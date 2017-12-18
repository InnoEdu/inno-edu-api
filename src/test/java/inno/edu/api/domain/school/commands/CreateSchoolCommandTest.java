package inno.edu.api.domain.school.commands;

import inno.edu.api.domain.school.commands.mappers.CreateSchoolRequestMapper;
import inno.edu.api.domain.school.models.School;
import inno.edu.api.domain.school.repositories.SchoolRepository;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.SchoolFactory.createStanfordRequest;
import static inno.edu.api.support.SchoolFactory.newStanford;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateSchoolCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateSchoolRequestMapper createSchoolRequestMapper;

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private CreateSchoolCommand createSchoolCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());
        when(createSchoolRequestMapper.toSchool(createStanfordRequest())).thenReturn(newStanford(null));
    }

    @Test
    public void shouldCallRepositoryToSaveSchool() {
        School newSchool = newStanford(uuidGeneratorService.generate());

        when(schoolRepository.save(newSchool)).thenReturn(newSchool);

        School savedSchool = createSchoolCommand.run(createStanfordRequest());
        assertThat(savedSchool, is(newSchool));
    }
}