package inno.edu.api.domain.user.transaction.models.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.controllers.user.TransactionController;
import inno.edu.api.controllers.user.UserController;
import inno.edu.api.domain.user.transaction.models.Transaction;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Getter
public class TransactionResource extends ResourceSupport {

    @JsonUnwrapped
    private final Transaction transaction;

    public TransactionResource(Transaction transaction) {
        this.transaction = transaction;

        add(linkTo(methodOn(TransactionController.class).get(transaction.getId())).withSelfRel());
        add(linkTo(methodOn(UserController.class).get(transaction.getUserId())).withRel("user"));
    }

    public ResponseEntity<Transaction> toCreated() {
        return created(URI.create(getLink("self").getHref())).body(transaction);
    }

    public ResponseEntity<Transaction> toUpdated() {
        return created(fromCurrentRequest().build().toUri()).body(transaction);
    }
}
