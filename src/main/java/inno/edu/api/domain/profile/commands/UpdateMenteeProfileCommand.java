package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.UpdateMenteeProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.UpdateMenteeProfileRequestMapper;
import inno.edu.api.domain.profile.models.MenteeProfile;
import inno.edu.api.domain.profile.queries.GetMenteeProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MenteeProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateMenteeProfileCommand {
    private final UpdateMenteeProfileRequestMapper updateMenteeProfileRequestMapper;

    private final GetMenteeProfileByIdQuery getMenteeProfileByIdQuery;
    private final MenteeProfileRepository profileRepository;

    public UpdateMenteeProfileCommand(UpdateMenteeProfileRequestMapper updateMenteeProfileRequestMapper, GetMenteeProfileByIdQuery getMenteeProfileByIdQuery, MenteeProfileRepository profileRepository) {
        this.updateMenteeProfileRequestMapper = updateMenteeProfileRequestMapper;
        this.getMenteeProfileByIdQuery = getMenteeProfileByIdQuery;
        this.profileRepository = profileRepository;
    }

    public MenteeProfile run(UUID id, UpdateMenteeProfileRequest updateMenteeProfileRequest) {
        MenteeProfile currentMenteeProfile = getMenteeProfileByIdQuery.run(id);
        updateMenteeProfileRequestMapper.setMenteeProfile(updateMenteeProfileRequest, currentMenteeProfile);
        return profileRepository.save(currentMenteeProfile);
    }
}
