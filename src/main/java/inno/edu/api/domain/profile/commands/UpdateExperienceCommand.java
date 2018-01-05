package inno.edu.api.domain.profile.commands;

import inno.edu.api.domain.profile.commands.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.commands.mappers.UpdateExperienceRequestMapper;
import inno.edu.api.domain.profile.models.Experience;
import inno.edu.api.domain.profile.queries.GetExperienceByIdQuery;
import inno.edu.api.domain.profile.repositories.ExperienceRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateExperienceCommand {
    private final UpdateExperienceRequestMapper updateExperienceRequestMapper;

    private final GetExperienceByIdQuery getExperienceByIdQuery;
    private final ExperienceRepository experienceRepository;

    public UpdateExperienceCommand(UpdateExperienceRequestMapper updateExperienceRequestMapper, GetExperienceByIdQuery getExperienceByIdQuery, ExperienceRepository experienceRepository) {
        this.updateExperienceRequestMapper = updateExperienceRequestMapper;
        this.getExperienceByIdQuery = getExperienceByIdQuery;
        this.experienceRepository = experienceRepository;
    }

    public Experience run(UUID id, UpdateExperienceRequest updateExperienceRequest) {
        Experience currentExperience = getExperienceByIdQuery.run(id);
        updateExperienceRequestMapper.setExperience(updateExperienceRequest, currentExperience);
        return experienceRepository.save(currentExperience);
    }
}
