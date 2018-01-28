package inno.edu.api.domain.user.transaction.models.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import inno.edu.api.domain.user.transaction.models.Balance;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Getter
public class BalanceResource extends ResourceSupport {

    @JsonUnwrapped
    private final Balance balance;

    public BalanceResource(Balance balance) {
        this.balance = balance;

//        add(linkTo(methodOn(TransactionController.class).balance(balance.getUserId())).withSelfRel());
//        add(linkTo(methodOn(TransactionController.class).all(balance.getUserId())).withRel("transactions"));
    }
}
