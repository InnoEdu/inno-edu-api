package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.UpdateMentorProfileRequest;
import inno.edu.api.domain.profile.commands.mappers.UpdateMentorProfileRequestMapper;
import inno.edu.api.domain.profile.models.MentorProfile;
import inno.edu.api.domain.profile.queries.GetMentorProfileByIdQuery;
import inno.edu.api.domain.profile.repositories.MentorProfileRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateMentorProfileCommand {
    private final UpdateMentorProfileRequestMapper updateMentorProfileRequestMapper;

    private final GetMentorProfileByIdQuery getMentorProfileByIdQuery;
    private final MentorProfileRepository profileRepository;

    public UpdateMentorProfileCommand(UpdateMentorProfileRequestMapper updateMentorProfileRequestMapper, GetMentorProfileByIdQuery getMentorProfileByIdQuery, MentorProfileRepository profileRepository) {
        this.updateMentorProfileRequestMapper = updateMentorProfileRequestMapper;
        this.getMentorProfileByIdQuery = getMentorProfileByIdQuery;
        this.profileRepository = profileRepository;
    }

    public MentorProfile run(UUID id, UpdateMentorProfileRequest updateMentorProfileRequest) {
        MentorProfile currentMentorProfile = getMentorProfileByIdQuery.run(id);
        updateMentorProfileRequestMapper.setMentorProfile(updateMentorProfileRequest, currentMentorProfile);
        return profileRepository.save(currentMentorProfile);
    }
}
