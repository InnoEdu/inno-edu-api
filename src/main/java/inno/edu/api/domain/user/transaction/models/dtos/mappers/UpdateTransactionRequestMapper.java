package inno.edu.api.domain.user.transaction.models.dtos.mappers;

import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.dtos.UpdateTransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UpdateTransactionRequestMapper {
    void setTransaction(UpdateTransactionRequest updateTransactionRequest,  @MappingTarget Transaction transaction);
}
