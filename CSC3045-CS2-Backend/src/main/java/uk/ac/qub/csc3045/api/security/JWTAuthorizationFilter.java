package uk.ac.qub.csc3045.api.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
import uk.ac.qub.csc3045.api.model.Account;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static uk.ac.qub.csc3045.api.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	/**
	 * Private variables
	 */
    private AuthenticationMapper mapper;

    /**
     * Constructor for Authorization - used to check if auth token is apart of 
     * request headers
     * @param authManager
     * @param mapper
     */
    public JWTAuthorizationFilter(AuthenticationManager authManager, AuthenticationMapper mapper) {
        super(authManager);
        this.mapper = mapper;
    }

    /**
     * 
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    /**
     * Checks request headers for auth token.
     * @param request
     * @return Authentication token
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject().toLowerCase();
            Account account = mapper.findAccountByEmail(user);
            if (account != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
