package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.mappers.CreateMenteeProfileRequestMapper;
import inno.edu.api.domain.profile.exceptions.MenteeProfileAlreadyCreatedException;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.domain.user.assertions.UserExistsAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.support.ProfileFactory.alanProfile;
import static inno.edu.api.support.ProfileFactory.createAlanProfileRequest;
import static inno.edu.api.support.ProfileFactory.newAlanProfile;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateMenteeProfileCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateMenteeProfileRequestMapper createMenteeProfileRequestMapper;

    @Mock
    private UserExistsAssertion userExistsAssertion;

    @Mock
    private MenteeProfileRepository menteeProfileRepository;

    @InjectMocks
    private CreateMenteeProfileCommand createMenteeProfileCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createMenteeProfileRequestMapper.toMenteeProfile(createAlanProfileRequest()))
                .thenReturn(newAlanProfile(null));
    }

    @Test
    public void shouldSaveNewMenteeProfile() {
        MenteeProfile newProfile = newAlanProfile(uuidGeneratorService.generate());
        when(menteeProfileRepository.save(newProfile)).thenReturn(newProfile);

        MenteeProfile savedProfile = createMenteeProfileCommand.run(createAlanProfileRequest());
        assertThat(savedProfile, is(newProfile));
    }

    @Test(expected = MenteeProfileAlreadyCreatedException.class)
    public void shouldNotAllowMultipleMenteeProfiles() {
        when(menteeProfileRepository.existsByMenteeId(alanProfile().getMenteeId())).thenReturn(true);

        createMenteeProfileCommand.run(createAlanProfileRequest());
    }

    @Test
    public void shouldRunAllAssertions() {
        createMenteeProfileCommand.run(createAlanProfileRequest());

        verify(userExistsAssertion).run(alanProfile().getMenteeId());
    }
}