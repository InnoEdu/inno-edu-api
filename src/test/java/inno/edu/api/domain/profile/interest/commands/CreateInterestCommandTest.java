package inno.edu.api.domain.profile.interest.commands;

import inno.edu.api.domain.profile.interest.commands.mappers.CreateInterestRequestMapper;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.domain.profile.root.assertions.ProfileExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.createFeiInterestRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.newFeiInterest;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateInterestCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateInterestRequestMapper createInterestRequestMapper;

    @Mock
    private ProfileExistsAssertion profileExistsAssertion;

    @Mock
    private InterestRepository interestRepository;

    @InjectMocks
    private CreateInterestCommand createInterestCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createInterestRequestMapper.toInterest(createFeiInterestRequest()))
                .thenReturn(newFeiInterest(null));
    }

    @Test
    public void shouldSaveNewProfile() {
        Interest newInterest = newFeiInterest(uuidGeneratorService.generate());
        when(interestRepository.save(newInterest)).thenReturn(newInterest);

        Interest savedProfile = createInterestCommand.run(feiProfile().getId(), createFeiInterestRequest());
        assertThat(savedProfile, is(newInterest));
    }

    @Test
    public void shouldRunAllAssertions() {
        createInterestCommand.run(feiProfile().getId(), createFeiInterestRequest());

        verify(profileExistsAssertion).run(feiProfile().getId());
    }
}