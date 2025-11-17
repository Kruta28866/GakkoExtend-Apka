// src/main/java/org/example/gakkoextend/security/GakkoUserDetailsService.java
package org.example.gakkoextend.service;

import org.example.gakkoextend.entity.AppUser;
import org.example.gakkoextend.repository.AppUserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GakkoUserDetailsService implements UserDetailsService {

    private final AppUserRepo users;

    public GakkoUserDetailsService(AppUserRepo users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // u Ciebie username = email
        AppUser u = users.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + username));

        Collection<GrantedAuthority> authorities = u.getRoles().stream()
                .map(r -> r.getName())                       // np. "STUDENT", "TEACHER"
                .map(name -> name.startsWith("ROLE_") ? name : "ROLE_" + name) // Spring wymaga "ROLE_*"
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return User.withUsername(u.getEmail())
                .password(u.getPasswordHash())               // masz kolumnę password_hash
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
