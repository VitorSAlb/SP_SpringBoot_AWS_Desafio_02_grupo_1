//package com.compass.desafio02.domain.services;
//
//import com.compass.desafio02.domain.entities.User;
//import com.compass.desafio02.domain.entities.enums.Role;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class JwtUserDetailsServiceTest {
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private JwtUserDetailsService jwtUserDetailsService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testLoadUserByUsername_Success() {
//        // Dados de exemplo
//        User user = new User();
//        user.setEmail("test@test.com");
//        user.setRole(Role.ROLE_STUDENT);  // Use uma role existente
//
//        when(userService.findByEmail("test@test.com")).thenReturn(user);
//
//        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("test@test.com");
//
//        assertNotNull(userDetails);
//        assertEquals("test@test.com", userDetails.getUsername());
//        verify(userService, times(1)).findByEmail("test@test.com");
//    }
//
//
//    @Test
//    void testLoadUserByUsername_UserNotFound() {
//        // Mock de comportamento quando o usuário não é encontrado
//        when(userService.findByEmail(anyString())).thenReturn(null);
//
//        // Execução do método e verificação da exceção
//        assertThrows(UsernameNotFoundException.class, () ->
//                jwtUserDetailsService.loadUserByUsername("nonexistent@test.com"));
//
//        verify(userService, times(1)).findByEmail(anyString());
//    }
//
//}
