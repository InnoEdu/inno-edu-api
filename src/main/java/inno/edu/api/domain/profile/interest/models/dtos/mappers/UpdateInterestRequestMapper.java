package inno.edu.api.domain.profile.interest.models.dtos.mappers;

import inno.edu.api.domain.profile.interest.models.dtos.UpdateInterestRequest;
import inno.edu.api.domain.profile.interest.models.Interest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateInterestRequestMapper {
    void setInterest(UpdateInterestRequest updateInterestRequest, @MappingTarget Interest experience);
}
