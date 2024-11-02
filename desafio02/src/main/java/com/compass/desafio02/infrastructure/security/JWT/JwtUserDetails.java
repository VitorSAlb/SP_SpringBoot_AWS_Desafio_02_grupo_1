package com.compass.desafio02.infrastructure.security.JWT;

import com.compass.desafio02.domain.entities.User;
import com.compass.desafio02.domain.entities.enums.Role;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtUserDetails extends User {

    private User user;

    public JwtUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Integer getId() {
        return this.user.getId();
    }

    public Role getRole() {
        return this.user.getRole();
    }
}
