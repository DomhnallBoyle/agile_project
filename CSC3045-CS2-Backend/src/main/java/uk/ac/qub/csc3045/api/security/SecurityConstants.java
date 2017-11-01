package uk.ac.qub.csc3045.api.security;

public class SecurityConstants {
    public static final String SECRET = "xGYtv9CR8xbre8";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/authentication/register";
    public static final String LOGIN_URL = "/authentication/login";
    public static final String SERVER_USERNAME = "csc3045cs2test@gmail.com";
	public static final String SERVER_PASSWORD = "CR8xbre8";
}