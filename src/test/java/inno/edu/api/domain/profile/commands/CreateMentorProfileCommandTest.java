package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.mappers.CreateMentorProfileRequestMapper;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.domain.school.assertions.SchoolExistsAssertion;
import inno.edu.api.domain.user.assertions.UserIsMentorAssertion;
import inno.edu.api.infrastructure.services.UUIDGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static inno.edu.api.domain.profile.models.ProfileStatus.CREATED;
import static inno.edu.api.support.ProfileFactory.createFeiProfileRequest;
import static inno.edu.api.support.ProfileFactory.feiProfile;
import static inno.edu.api.support.ProfileFactory.newFeiProfile;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateMentorProfileCommandTest {
    @Mock
    private UUIDGeneratorService uuidGeneratorService;

    @Mock
    private CreateMentorProfileRequestMapper createMentorProfileRequestMapper;

    @Mock
    private UserIsMentorAssertion userIsMentorAssertion;

    @Mock
    private SchoolExistsAssertion schoolExistsAssertion;

    @Mock
    private MentorProfileRepository mentorProfileRepository;

    @Mock
    private DeactivateMentorProfilesCommand deactivateMentorProfilesCommand;

    @InjectMocks
    private CreateMentorProfileCommand createMentorProfileCommand;

    @Before
    public void setUp() {
        when(uuidGeneratorService.generate()).thenReturn(randomUUID());

        when(createMentorProfileRequestMapper.toMentorProfile(createFeiProfileRequest()))
                .thenReturn(newFeiProfile(null, null));
    }

    @Test
    public void shouldSaveNewMentorProfile() {
        MentorProfile newProfile = newFeiProfile(uuidGeneratorService.generate(), CREATED);
        when(mentorProfileRepository.save(newProfile)).thenReturn(newProfile);

        MentorProfile savedProfile = createMentorProfileCommand.run(createFeiProfileRequest());
        assertThat(savedProfile, is(newProfile));
    }

    @Test
    public void shouldDeactivateOtherMentorProfiles() {
        createMentorProfileCommand.run(createFeiProfileRequest());

        verify(deactivateMentorProfilesCommand).run(feiProfile().getMentorId());
    }

    @Test
    public void shouldRunAllAssertions() {
        createMentorProfileCommand.run(createFeiProfileRequest());

        verify(userIsMentorAssertion).run(feiProfile().getMentorId());
        verify(schoolExistsAssertion).run(feiProfile().getSchoolId());

    }
}