package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.repositories.UserRepository;
import com.compass.desafio02.infrastructure.exceptions.InvalidCredentialsException;
import com.compass.desafio02.web.dto.LoginRequestDTO;
import com.compass.desafio02.web.dto.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private static final Long EXPIRESIN = 30000000L;

    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public LoginResponseDTO authUser (LoginRequestDTO requestDTO){

        var user = userRepository.findByEmail(requestDTO.email());

        if (user == null || !user.isLoginCorrect(requestDTO, passwordEncoder)){
            throw new InvalidCredentialsException("User or password is invalid!");
        }

        var roles = user.getRole();

        var claims = JwtClaimsSet.builder()
                .issuer("to.do.list.backend")
                .subject(user.getEmail())
                .claim("roles", roles)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(EXPIRESIN))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(jwtValue, EXPIRESIN);
    }


}