package inno.edu.api.infrastructure.security.jwt;

class SecurityConstants {
    static final String SECRET = "d2376646-c600-42c0-808f-b6a7642439c7";
    static final long EXPIRATION_TIME = 864_000_000;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
}
