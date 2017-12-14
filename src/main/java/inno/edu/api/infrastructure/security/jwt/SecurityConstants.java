package inno.edu.api.infrastructure.security.jwt;

public class SecurityConstants {
    public static final String SECRET = "d2376646-c600-42c0-808f-b6a7642439c7";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SCOPE_ACCESS = "scopes";
    public static final String AUTH_URL = "/api/auth/login";
}
