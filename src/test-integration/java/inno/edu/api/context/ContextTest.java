package inno.edu.api.context;

import inno.edu.api.IntegrationTest;
import inno.edu.api.controllers.UserController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ContextTest extends IntegrationTest {
    @Autowired
    private UserController userController;

    @Test
    public void shouldLoadTestContext() {
        assertThat(userController, is(notNullValue()));
    }
}
