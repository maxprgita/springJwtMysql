package it.aips.securityJWT.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomJwtTokenValidationFilter extends OncePerRequestFilter{
	
    private final String JWT_KEY = "jxgEQe.XHuPq8VdbyYFNkAN.dudQ0903YUn4";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String REFRESH_HEADER = "RefreshToken";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        logger.info("Request received for Jwt token validation");

        validateJwtToken(request, response, false);
        filterChain.doFilter(request, response);
		
	}
	
    public void validateJwtToken(HttpServletRequest request, HttpServletResponse response, final boolean isRefreshValidation) {

        final String token = request.getHeader(AUTHORIZATION_HEADER);
        final String refresh = request.getHeader(REFRESH_HEADER);

        //logger.info("Authorization Token: {}", token);

        if (token != null && !token.contains("Basic")) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        JWT_KEY.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(isRefreshValidation ? refresh : token)
                        .getBody();

                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        getStudentRoles(authorities));

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (ExpiredJwtException ex) {
                logger.info("Token expired!");

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!");
            }
        }

    }

    private List<GrantedAuthority> getStudentRoles(String authorities) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        String[] roles = authorities.split(",");
        for (String role : roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.replaceAll("\\s+", "")));
        }

        return grantedAuthorityList;
    }

}
