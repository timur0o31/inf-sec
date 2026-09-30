package io.github.timur0o31.lab1_inf.security;

import io.github.timur0o31.lab1_inf.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.JwtException;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final ObjectWriter jsonMapper;
    public JwtFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService, JsonMapper jsonMapper) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.jsonMapper = jsonMapper.writerFor(ErrorResponse.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7).trim();
        try{
            authenticateUser(token);
        }catch (AuthenticationException | JwtException exception) {
            SecurityContextHolder.clearContext();
            response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            ErrorResponse error = new ErrorResponse(401, "JWT недействителен или просрочен");
            jsonMapper.writeValue(response.getOutputStream(), error);
            return;
        }
        filterChain.doFilter(request,response);
    }

    private void authenticateUser(String token) {
        if (token.isEmpty()) throw new BadCredentialsException("JWT-токен отсутствует");
        String username = jwtUtils.getUsernameFromToken(token);
        if (username == null || username.isBlank()) throw new BadCredentialsException("Username отсутствует");
        var user = userDetailsService.loadUserByUsername(username);
        var authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities());
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
