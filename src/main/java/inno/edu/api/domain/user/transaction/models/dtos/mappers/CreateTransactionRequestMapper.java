package inno.edu.api.domain.user.transaction.models.dtos.mappers;

import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.dtos.CreateTransactionRequest;
import org.mapstruct.Mapper;

@Mapper
public interface CreateTransactionRequestMapper {
    Transaction toTransaction(CreateTransactionRequest createTransactionRequest);
}
