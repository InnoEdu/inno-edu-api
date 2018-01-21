package inno.edu.api.domain.user.transaction.repositories;

import inno.edu.api.domain.user.transaction.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
}