package inno.edu.api.controllers.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Streams.stream;

@Component
public class ResourceBuilder {
    private <T> Resources<T> build(List<T> resourceItems) {
        Resources<T> resources = new Resources<>(resourceItems);

        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));

        return resources;
    }

    public <T, R extends ResourceSupport> Resources<R> from(Iterable<T> models, Function<T, R> resourceMapper) {
        List<R> resourcesItems = stream(models)
                .map(resourceMapper)
                .collect(Collectors.toList());

        return build(resourcesItems);
    }
}
