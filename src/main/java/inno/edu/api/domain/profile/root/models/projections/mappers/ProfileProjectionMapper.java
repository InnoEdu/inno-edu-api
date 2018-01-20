package inno.edu.api.domain.profile.root.models.projections.mappers;

import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.models.projections.ProfileProjection;
import inno.edu.api.domain.profile.root.models.projections.mappers.decorators.ProfileProjectionMapperDecorator;
import inno.edu.api.domain.profile.root.models.resources.ProfileProjectionResource;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(ProfileProjectionMapperDecorator.class)
public interface ProfileProjectionMapper {
    ProfileProjection toProfileProjection(Profile profile);

    ProfileProjectionResource toProfileProjectionResource(Profile profile);
}
