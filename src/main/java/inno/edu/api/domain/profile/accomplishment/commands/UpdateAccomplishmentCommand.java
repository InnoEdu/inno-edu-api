package inno.edu.api.domain.profile.accomplishment.commands;

import inno.edu.api.domain.profile.accomplishment.models.dtos.UpdateAccomplishmentRequest;
import inno.edu.api.domain.profile.accomplishment.models.dtos.mappers.UpdateAccomplishmentRequestMapper;
import inno.edu.api.domain.profile.accomplishment.models.Accomplishment;
import inno.edu.api.domain.profile.accomplishment.queries.GetAccomplishmentByIdQuery;
import inno.edu.api.domain.profile.accomplishment.repositories.AccomplishmentRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateAccomplishmentCommand {
    private final UpdateAccomplishmentRequestMapper updateAccomplishmentRequestMapper;

    private final GetAccomplishmentByIdQuery getAccomplishmentByIdQuery;
    private final AccomplishmentRepository accomplishmentRepository;

    public UpdateAccomplishmentCommand(UpdateAccomplishmentRequestMapper updateAccomplishmentRequestMapper, GetAccomplishmentByIdQuery getAccomplishmentByIdQuery, AccomplishmentRepository accomplishmentRepository) {
        this.updateAccomplishmentRequestMapper = updateAccomplishmentRequestMapper;
        this.getAccomplishmentByIdQuery = getAccomplishmentByIdQuery;
        this.accomplishmentRepository = accomplishmentRepository;
    }

    public Accomplishment run(UUID id, UpdateAccomplishmentRequest updateAccomplishmentRequest) {
        Accomplishment currentAccomplishment = getAccomplishmentByIdQuery.run(id);
        updateAccomplishmentRequestMapper.setAccomplishment(updateAccomplishmentRequest, currentAccomplishment);
        return accomplishmentRepository.save(currentAccomplishment);
    }
}
