package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.User;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.infrastructure.security.JWT.JwtToken;
import com.compass.desafio02.infrastructure.security.JWT.JwtUserDetails;
import com.compass.desafio02.util.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService UserService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = UserService.findByEmail(email);
        return (UserDetails) new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String email) {
        User user = UserService.findByEmail(email);
        Role role = user.getRole();
        return JwtUtils.createToken(email, role.name().substring("ROLE_".length()));
    }
}
