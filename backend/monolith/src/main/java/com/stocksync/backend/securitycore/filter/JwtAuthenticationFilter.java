package com.stocksync.backend.securitycore.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORITIES = "authorities";
    private static final String TENANT_ID = "tenant_id";

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(BEARER.length());

        try {
            Jwt jwt = jwtDecoder.decode(token);
            String userId = jwt.getSubject();
            String tenantId = jwt.getClaimAsString(TENANT_ID);
            List<String> roles = jwt.getClaimAsStringList(AUTHORITIES);
            
            if (tenantId == null || roles == null || roles.isEmpty()) {
                throw new IllegalStateException("Missing critical JWT claims (tenant_id or roles).");
            }
            
            Collection<? extends GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userId, 
                    null, 
                    authorities);
            authenticationToken.setDetails(tenantId); 
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (JwtValidationException | IllegalStateException e) {
            log.debug("JWT validation failed: {}", e.getMessage());
            SecurityContextHolder.clearContext(); 
        } catch (Exception e) {
            log.error("Unexpected error during JWT processing: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

}