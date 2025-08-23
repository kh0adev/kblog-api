package com.kblog.auth.services;

import com.kblog.user.models.User;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    @Nullable
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        var authorities = new JwtGrantedAuthoritiesConverter().convert(source);
        Long userId = source.getClaim("userId");
        String userName = source.getSubject();
        
        var user = User.builder().id(userId).userName(userName).build();

        return new UsernamePasswordAuthenticationToken(user, source, authorities);
    }

}