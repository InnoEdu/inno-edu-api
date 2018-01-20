package inno.edu.api.domain.profile.experience.commands;

import inno.edu.api.domain.profile.experience.models.dtos.UpdateExperienceRequest;
import inno.edu.api.domain.profile.experience.models.dtos.mappers.UpdateExperienceRequestMapper;
import inno.edu.api.domain.profile.experience.models.Experience;
import inno.edu.api.domain.profile.experience.queries.GetExperienceByIdQuery;
import inno.edu.api.domain.profile.experience.repositories.ExperienceRepository;
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
