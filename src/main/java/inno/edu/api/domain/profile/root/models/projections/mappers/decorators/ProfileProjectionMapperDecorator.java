package inno.edu.api.domain.profile.root.models.projections.mappers.decorators;

import inno.edu.api.domain.profile.root.models.Profile;
import inno.edu.api.domain.profile.root.models.projections.ProfileProjection;
import inno.edu.api.domain.profile.root.models.projections.mappers.ProfileProjectionMapper;
import inno.edu.api.domain.profile.root.models.resources.ProfileProjectionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class ProfileProjectionMapperDecorator implements ProfileProjectionMapper {

    @Autowired
    @Qualifier("delegate")
    private ProfileProjectionMapper delegate;

    @Override
    public ProfileProjection toProfileProjection(Profile profile) {
        ProfileProjection projection = delegate.toProfileProjection(profile);
        projection.setMentor(profile.getSchoolId() != null);
        return projection;
    }

    @Override
    public ProfileProjectionResource toProfileProjectionResource(Profile appointment) {
        return new ProfileProjectionResource(toProfileProjection(appointment));
    }
}
