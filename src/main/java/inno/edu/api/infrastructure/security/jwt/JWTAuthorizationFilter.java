package inno.edu.api.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

import static inno.edu.api.infrastructure.configuration.StaticConstants.APPLICATION_JSON;
import static inno.edu.api.infrastructure.configuration.StaticConstants.UTF8;
import static inno.edu.api.infrastructure.security.SecurityConstants.EXPIRED_TOKEN_JSON;
import static inno.edu.api.infrastructure.security.SecurityConstants.HEADER_STRING;
import static inno.edu.api.infrastructure.security.SecurityConstants.SCOPE_ACCESS;
import static inno.edu.api.infrastructure.security.SecurityConstants.SECRET;
import static inno.edu.api.infrastructure.security.SecurityConstants.TOKEN_PREFIX;
import static java.lang.String.format;
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

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(httpServletRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ExpiredJwtException expiredJwtException) {
            sendHttpError(expiredJwtException, httpServletResponse);
        }
    }

    private void sendHttpError(ExpiredJwtException expiredJwtException, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.setCharacterEncoding(UTF8);
        httpServletResponse.getWriter().write(format(EXPIRED_TOKEN_JSON, expiredJwtException.getMessage()));
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
                String roles = (String) jwtClaims.getOrDefault(SCOPE_ACCESS, "");

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