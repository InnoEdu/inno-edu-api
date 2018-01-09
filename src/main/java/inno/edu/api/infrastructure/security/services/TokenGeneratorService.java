package inno.edu.api.infrastructure.security.services;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static inno.edu.api.infrastructure.security.SecurityConstants.EXPIRATION_TIME;
import static inno.edu.api.infrastructure.security.SecurityConstants.SCOPE_ACCESS;
import static inno.edu.api.infrastructure.security.SecurityConstants.SECRET;
import static inno.edu.api.infrastructure.security.SecurityConstants.TOKEN_PREFIX;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.lang.String.join;

@Service
public class TokenGeneratorService {
    public String generate(String username, List<String> roles) {
        return TOKEN_PREFIX + Jwts.builder()
                .claim(SCOPE_ACCESS, join(",", roles))
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(HS256, SECRET.getBytes())
                .compact();
    }
}
