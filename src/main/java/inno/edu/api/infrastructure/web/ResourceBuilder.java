package inno.edu.api.infrastructure.web;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Streams.stream;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

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
                .collect(toList());

        return build(resourcesItems);
    }

    public <T, R extends ResourceSupport> Resources<Object> wrappedFrom(Iterable<T> models, Function<T, R> resourceMapper, Class clazz) {
        List<Object> resourcesItems = stream(models)
                .map(resourceMapper)
                .map(r -> (Object)r)
                .collect(toList());

        EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
        if (resourcesItems.isEmpty()) {
            return build(singletonList(wrappers.emptyCollectionOf(clazz)));
        }

        return build(resourcesItems);
    }
}
