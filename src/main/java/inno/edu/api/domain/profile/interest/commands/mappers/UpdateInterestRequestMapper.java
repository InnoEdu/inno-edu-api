package inno.edu.api.domain.profile.interest.commands.mappers;

import inno.edu.api.domain.profile.interest.commands.dtos.UpdateInterestRequest;
import inno.edu.api.domain.profile.interest.models.Interest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateInterestRequestMapper {
    void setInterest(UpdateInterestRequest updateInterestRequest, @MappingTarget Interest experience);
}
