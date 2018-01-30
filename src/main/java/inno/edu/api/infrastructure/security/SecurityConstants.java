package inno.edu.api.infrastructure.security;

public class SecurityConstants {
    public static final String SECRET = "d2376646-c600-42c0-808f-b6a7642439c7";
    public static final long EXPIRATION_TIME = 31556926000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SCOPE_ACCESS = "scopes";
    public static final String AUTH_URL = "/api/auth/login";

    public static final String EXPIRED_TOKEN_JSON = "[\n" +
            "    {\n" +
            "        \"logref\": \"error\",\n" +
            "        \"message\": \"%s\",\n" +
            "        \"links\": []\n" +
            "    }\n" +
            "]";
}
