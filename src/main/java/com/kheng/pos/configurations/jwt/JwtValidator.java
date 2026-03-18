package com.kheng.pos.configurations.jwt;

import com.kheng.pos.configurations.jwt.constant.JwtConstant;
import com.kheng.pos.configurations.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtValidator extends OncePerRequestFilter {
    
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null && !jwt.isEmpty()) {
            try {
                String email = jwtService.getEmailFromToken(jwt);
                String authorities = jwtService.getAuthoritiesFromToken(jwt);

                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication auth = new UsernamePasswordAuthenticationToken(email, null, auths);
                SecurityContextHolder.getContext().setAuthentication(auth);
                
                log.debug("JWT token validated for user: {}", email);
            } catch (Exception e) {
                log.warn("JWT validation failed: {}", e.getMessage());
                throw new BadCredentialsException("Invalid or expired JWT token");
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
