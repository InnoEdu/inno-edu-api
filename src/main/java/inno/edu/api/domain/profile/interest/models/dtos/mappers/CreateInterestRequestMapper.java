package inno.edu.api.domain.profile.interest.models.dtos.mappers;

import inno.edu.api.domain.profile.interest.models.dtos.CreateInterestRequest;
import inno.edu.api.domain.profile.interest.models.Interest;
import org.mapstruct.Mapper;

@Mapper
public interface CreateInterestRequestMapper {
    Interest toInterest(CreateInterestRequest createInterestRequest);
}
