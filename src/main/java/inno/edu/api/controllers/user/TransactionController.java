package inno.edu.api.controllers.user;

import inno.edu.api.domain.user.transaction.commands.CreateTransactionCommand;
import inno.edu.api.domain.user.transaction.commands.DeleteTransactionCommand;
import inno.edu.api.domain.user.transaction.commands.UpdateTransactionCommand;
import inno.edu.api.domain.user.transaction.models.Transaction;
import inno.edu.api.domain.user.transaction.models.dtos.CreateTransactionRequest;
import inno.edu.api.domain.user.transaction.models.dtos.UpdateTransactionRequest;
import inno.edu.api.domain.user.transaction.models.resources.BalanceResource;
import inno.edu.api.domain.user.transaction.models.resources.TransactionResource;
import inno.edu.api.domain.user.transaction.queries.GetBalanceByUserIdQuery;
import inno.edu.api.domain.user.transaction.queries.GetTransactionByIdQuery;
import inno.edu.api.domain.user.transaction.queries.GetTransactionsByUserIdQuery;
import inno.edu.api.infrastructure.web.ResourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class TransactionController {
    private final ResourceBuilder resourceBuilder;

    private final GetTransactionByIdQuery getTransactionByIdQuery;
    private final GetTransactionsByUserIdQuery getTransactionsByUserIdQuery;

    private final CreateTransactionCommand createTransactionCommand;
    private final UpdateTransactionCommand updateTransactionCommand;
    private final DeleteTransactionCommand deleteTransactionCommand;

    private final GetBalanceByUserIdQuery getBalanceByUserIdQuery;

    @Autowired
    public TransactionController(ResourceBuilder resourceBuilder, GetTransactionByIdQuery getTransactionByIdQuery, GetTransactionsByUserIdQuery getTransactionsByUserIdQuery, CreateTransactionCommand createTransactionCommand, UpdateTransactionCommand updateTransactionCommand, DeleteTransactionCommand deleteTransactionCommand, GetBalanceByUserIdQuery getBalanceByUserIdQuery) {
        this.resourceBuilder = resourceBuilder;
        this.getTransactionByIdQuery = getTransactionByIdQuery;
        this.getTransactionsByUserIdQuery = getTransactionsByUserIdQuery;
        this.createTransactionCommand = createTransactionCommand;
        this.updateTransactionCommand = updateTransactionCommand;
        this.deleteTransactionCommand = deleteTransactionCommand;
        this.getBalanceByUserIdQuery = getBalanceByUserIdQuery;
    }

    @GetMapping("/{id}/balance")
    public BalanceResource balance(@PathVariable UUID id) {
        return new BalanceResource(getBalanceByUserIdQuery.run(id));
    }

    @GetMapping("/{id}/transactions")
    public Resources<Object> all(@PathVariable UUID id) {
        Iterable<Transaction> transactions = getTransactionsByUserIdQuery.run(id);
        return resourceBuilder.wrappedFrom(transactions, TransactionResource::new, TransactionResource.class);
    }

    @GetMapping("/transactions/{id}")
    public TransactionResource get(@PathVariable UUID id) {
        return new TransactionResource(getTransactionByIdQuery.run(id));
    }

    @PostMapping("/{id}/transactions")
    public ResponseEntity<Transaction> post(@PathVariable UUID id, @Valid @RequestBody CreateTransactionRequest request) {
        TransactionResource transactionResource = new TransactionResource(createTransactionCommand.run(id, request));
        return transactionResource.toCreated();
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> put(@PathVariable UUID id, @Valid @RequestBody UpdateTransactionRequest request) {
        TransactionResource transactionResource = new TransactionResource(updateTransactionCommand.run(id, request));
        return transactionResource.toUpdated();
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        deleteTransactionCommand.run(id);
        return noContent().build();
    }
}
