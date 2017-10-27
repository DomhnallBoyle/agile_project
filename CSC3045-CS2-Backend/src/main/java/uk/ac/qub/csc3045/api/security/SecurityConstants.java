package uk.ac.qub.csc3045.api.security;

public class SecurityConstants {
	public static final String SECRET = "CSC3045APISecret";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/authentication/register";
    public static final String LOGIN_URL = "/login";
}