package com.example.springsecurityfirsthw.filter;

import com.example.springsecurityfirsthw.model.JwtAuthenticationToken;
import com.example.springsecurityfirsthw.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

        private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        UserDetails userDetails = extractUserDetailsFromToken(token);
        if (JwtUtil.validateToken(token, userDetails)) {
            return new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        } else {
            throw new AuthenticationException("JWT Token неверный") {};
        }
    }

    private UserDetails extractUserDetailsFromToken(String token) {
        String username = JwtUtil.extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
