package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.User;
import com.compass.desafio02.domain.repositories.UserRepository;
import com.compass.desafio02.infrastructure.exceptions.user.PasswordUpdateException;
import com.compass.desafio02.infrastructure.exceptions.user.UserCreationException;
import com.compass.desafio02.infrastructure.exceptions.user.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User Save(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException e){
            throw new UserCreationException(String.format("Username '%s' '%s', already registered",user.getFirstName(), user.getLastName()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id '%s' not found", id))
        );
    }

    @Transactional
    public User editPass(Integer id, String oldPass, String newPass, String confirmPass) {
        if(!newPass.equals(confirmPass)){
            throw new PasswordUpdateException(String.format("New password does not match confirm password"));
        }

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id '%s' not found", id)));
        if(!passwordEncoder.matches(oldPass, user.getPassword())){
            throw new PasswordUpdateException(String.format("Old password does not match confirm password"));
        }

        user.setPassword(passwordEncoder.encode(newPass));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmailUser(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with email '%s' not found", email))
        );
    }
}
