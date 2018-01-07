package inno.edu.api.domain.common.assertions;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
import java.util.function.Function;

public class EntityExistsAssertion<T extends CrudRepository, E extends RuntimeException, ES extends Function<UUID, E>> {
    private final T repository;
    private final ES exceptionSupplier;

    protected EntityExistsAssertion(T repository, ES exceptionSupplier) {
        this.repository = repository;
        this.exceptionSupplier = exceptionSupplier;
    }

    public void run(UUID id) {
        //noinspection unchecked
        if (!repository.exists(id)) {
            throw exceptionSupplier.apply(id);
        }
    }
}
