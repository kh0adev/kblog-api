package com.kblog.auth.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.kblog.user.models.User;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    @Nullable
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        var authorities = extractAuthorities(source);
        Long userId = source.getClaim("userId");
        String userName = source.getSubject();
        
        var user = User.builder().id(userId).userName(userName).build();

        return new UsernamePasswordAuthenticationToken(user, source, authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        var roles = jwt.getClaimAsStringList("roles");
        if (roles == null) return List.of();

        return roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role) // đảm bảo đúng prefix
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}