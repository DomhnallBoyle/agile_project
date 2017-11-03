	package uk.ac.qub.csc3045.api.security;
	
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Date;
	
	import javax.servlet.FilterChain;
	import javax.servlet.ServletException;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.AuthenticationException;
	import org.springframework.security.core.userdetails.User;
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
	
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.fasterxml.jackson.databind.ObjectWriter;
	
	import static uk.ac.qub.csc3045.api.security.SecurityConstants.*;
	
	import io.jsonwebtoken.Jwts;
	import io.jsonwebtoken.SignatureAlgorithm;
	import net.minidev.json.JSONObject;
	import uk.ac.qub.csc3045.api.mapper.AuthenticationMapper;
	import uk.ac.qub.csc3045.api.model.Account;
	
	public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	    private AuthenticationManager authenticationManager;
	    private AuthenticationMapper authenticationMapper;
	
	    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationMapper authenticationMapper) {
	        this.authenticationManager = authenticationManager;
	        this.authenticationMapper = authenticationMapper;
	    }
	
	    //Takes in account object from login request, and checks if credentials are valid
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res) throws AuthenticationException {
	    try {
	        Account creds = new ObjectMapper()
	                .readValue(req.getInputStream(), Account.class);
	
	        return authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        creds.getUsername(),
	                        creds.getPassword(),
	                        new ArrayList<>())
	        );
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	    super.setAuthenticationManager(authenticationManager);
	}
	
	//if credentials were valid builds the token and sends the response
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
	                                        HttpServletResponse res,
	                                        FilterChain chain,
	                                        Authentication auth) throws IOException, ServletException {
		//add token
	    String token = Jwts.builder()
	            .setSubject(((User) auth.getPrincipal()).getUsername())
	            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	            .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
	            .compact();
	    res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	    
	    //add user object for the account to body
	    res.setContentType("application/json");
	        Account account = authenticationMapper.findAccountByUsername(((User) auth.getPrincipal()).getUsername());
	        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	        String json = ow.writeValueAsString(account.getUser());
	        res.getWriter().write(json);
	    }
	    
	}