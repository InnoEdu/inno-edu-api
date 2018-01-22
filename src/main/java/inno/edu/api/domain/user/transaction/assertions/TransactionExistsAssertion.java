package inno.edu.api.domain.transaction.transaction.assertions;

import inno.edu.api.domain.common.assertions.EntityExistsAssertion;
import inno.edu.api.domain.user.transaction.exceptions.TransactionNotFoundException;
import inno.edu.api.domain.user.transaction.repositories.TransactionRepository;
import inno.edu.api.infrastructure.annotations.Assertion;

import java.util.UUID;
import java.util.function.Function;

@Assertion
public class TransactionExistsAssertion extends EntityExistsAssertion<TransactionRepository, TransactionNotFoundException, Function<UUID, TransactionNotFoundException>> {
    protected TransactionExistsAssertion(TransactionRepository repository) {
        super(repository, TransactionNotFoundException::new);
    }
}
