package inno.edu.api.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static inno.edu.api.infrastructure.security.jwt.SecurityConstants.HEADER_STRING;
import static inno.edu.api.infrastructure.security.jwt.SecurityConstants.SECRET;
import static inno.edu.api.infrastructure.security.jwt.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        String header = httpServletRequest.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(httpServletRequest);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HEADER_STRING);

        if (token != null) {
            Claims jwtClaims = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String username = jwtClaims.getSubject();
            if (username != null) {
                String roles = (String) jwtClaims.getOrDefault("roles", "");

                List<GrantedAuthority> authorityList = roles.equals("") ?
                        Collections.emptyList() :
                        commaSeparatedStringToAuthorityList(roles);

                return new UsernamePasswordAuthenticationToken(username, null, authorityList);
            }
            return null;
        }
        return null;
    }
}