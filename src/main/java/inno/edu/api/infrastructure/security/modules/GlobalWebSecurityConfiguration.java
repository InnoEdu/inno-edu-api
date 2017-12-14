package inno.edu.api.infrastructure.security.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class GlobalWebSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {

        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);

        return expressionHandler;
    }
}
