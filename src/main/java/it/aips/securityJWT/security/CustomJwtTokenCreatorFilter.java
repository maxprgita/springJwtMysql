package it.aips.securityJWT.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomJwtTokenCreatorFilter extends OncePerRequestFilter{
	
    private final String JWT_KEY = "jxgEQe.XHuPq8VdbyYFNkAN.dudQ0903YUn4";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String REFRESH_HEADER = "RefreshToken";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        //logger.info("Token generator class");
        generateToken(request, response);
        filterChain.doFilter(request, response);	
	}
	
    public void generateToken(HttpServletRequest request, HttpServletResponse response) {

        //Get the username from authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) { //verify whether user is authenticated
            String username = authentication.getName();
            SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));

        /*    String jwt_token = Jwts.builder()
                    .setIssuer("aips-maxim")
                    .setExpiration(new Date((new Date()).getTime() + 30000))
                    .setSubject("aips_token")
                    .claim("username", username)
                    .claim("authorities", getStudentRoles((List<GrantedAuthority>) authentication.getAuthorities()))
                    .signWith(key)
                    .compact();	*/

            if (request.getHeader(REFRESH_HEADER) == null) {

           /*     String refresh_token = Jwts.builder()
                        .setIssuer("aips-maxim")
                        .setExpiration(new Date((new Date()).getTime() + 3000000))
                        .setSubject("aips_token")
                        .claim("username", username)
                        .claim("authorities", getStudentRoles((List<GrantedAuthority>) authentication.getAuthorities()))
                        .signWith(key)
                        .compact();

                response.setHeader(REFRESH_HEADER, refresh_token); */
                //logger.info("Refresh Token successfully generated: {}", refresh_token);
                response.setHeader(AUTHORIZATION_HEADER, creaToken(username, key, authentication));
            } else {
            	response.setHeader(AUTHORIZATION_HEADER, creaToken(username, key, authentication));
            }
           // response.setHeader(AUTHORIZATION_HEADER, jwt_token);
            //logger.info("Token successfully generated: {}", jwt_token);
        }
    }

    private String getStudentRoles(List<GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
    
    
    private String creaToken(String username, SecretKey key, Authentication authentication) {
    	
        String token = Jwts.builder()
                .setIssuer("aips-maxim")
                .setExpiration(new Date((new Date()).getTime() + 30000))
                .setSubject("aips_token")
                .claim("username", username)
                .claim("authorities", getStudentRoles((List<GrantedAuthority>) authentication.getAuthorities()))
                .signWith(key)
                .compact();
        
        return token;
    }

}
