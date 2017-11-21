package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.UpdateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.UpdateMenteeProfileRequestToMenteeProfileMapper;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateMenteeProfileCommand {
    private final UpdateMenteeProfileRequestToMenteeProfileMapper updateProfileToProfileMapper;

    private final GetMenteeProfileByIdQuery getMenteeProfileByIdQuery;
    private final MenteeProfileRepository profileRepository;

    public UpdateMenteeProfileCommand(UpdateMenteeProfileRequestToMenteeProfileMapper updateProfileToProfileMapper, GetMenteeProfileByIdQuery getMenteeProfileByIdQuery, MenteeProfileRepository profileRepository) {
        this.updateProfileToProfileMapper = updateProfileToProfileMapper;
        this.getMenteeProfileByIdQuery = getMenteeProfileByIdQuery;
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(UUID id, UpdateMenteeProfileRequest updateMenteeProfileRequest) {
        MenteeProfile currentMenteeProfile = getMenteeProfileByIdQuery.run(id);
        updateProfileToProfileMapper.updateMenteeProfileRequestToMenteeProfile(updateMenteeProfileRequest, currentMenteeProfile);
        return profileRepository.save(currentMenteeProfile);
    }
}
