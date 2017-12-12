package inno.edu.api.infrastructure;

import inno.edu.api.ApiTest;
import inno.edu.api.controllers.UserController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ContextTest extends ApiTest {
    @Autowired
    private UserController userController;

    @Test
    public void shouldLoadTestContext() {
        assertThat(userController, is(notNullValue()));
    }
}
