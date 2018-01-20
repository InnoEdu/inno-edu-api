package inno.edu.api.domain.profile.interest.commands;

import inno.edu.api.domain.profile.interest.models.dtos.UpdateInterestRequest;
import inno.edu.api.domain.profile.interest.models.dtos.mappers.UpdateInterestRequestMapper;
import inno.edu.api.domain.profile.interest.models.Interest;
import inno.edu.api.domain.profile.interest.queries.GetInterestByIdQuery;
import inno.edu.api.domain.profile.interest.repositories.InterestRepository;
import inno.edu.api.infrastructure.annotations.Command;

import java.util.UUID;

@Command
public class UpdateInterestCommand {
    private final UpdateInterestRequestMapper updateInterestRequestMapper;

    private final GetInterestByIdQuery getInterestByIdQuery;
    private final InterestRepository interestRepository;

    public UpdateInterestCommand(UpdateInterestRequestMapper updateInterestRequestMapper, GetInterestByIdQuery getInterestByIdQuery, InterestRepository interestRepository) {
        this.updateInterestRequestMapper = updateInterestRequestMapper;
        this.getInterestByIdQuery = getInterestByIdQuery;
        this.interestRepository = interestRepository;
    }

    public Interest run(UUID id, UpdateInterestRequest updateInterestRequest) {
        Interest currentInterest = getInterestByIdQuery.run(id);
        updateInterestRequestMapper.setInterest(updateInterestRequest, currentInterest);
        return interestRepository.save(currentInterest);
    }
}
