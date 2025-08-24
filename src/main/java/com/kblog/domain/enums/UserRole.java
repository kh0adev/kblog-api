package com.kblog.domain.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.LinkedList;
import java.util.List;

public enum UserRole {
        USER,
        ADMIN;

        public List<SimpleGrantedAuthority> getAuthorities() {
                var authorities = new LinkedList<SimpleGrantedAuthority>();

                authorities.add(new SimpleGrantedAuthority(this.name()));
                return authorities;
        }
}
