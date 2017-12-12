package inno.edu.api.infrastructure.security.jwt;

class SecurityConstants {
    static final String SECRET = "Bearer ";
    static final long EXPIRATION_TIME = 864_000_000;
    static final String TOKEN_PREFIX = "Inno ";
    static final String HEADER_STRING = "Authorization";
}
