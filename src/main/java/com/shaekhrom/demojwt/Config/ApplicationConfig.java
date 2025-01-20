package com.shaekhrom.demojwt.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shaekhrom.demojwt.User.UserRepository;

import lombok.RequiredArgsConstructor;

// Marca esta clase como una configuración de Spring para gestionar Beans.
@Configuration
// Genera un constructor para inyectar el UserRepository automáticamente.
@RequiredArgsConstructor
public class ApplicationConfig {

    // Repositorio de usuarios para interactuar con la base de datos.
    private final UserRepository userRepository;

    // Define el gestor de autenticación utilizando la configuración de Spring Security.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Obtiene y retorna el gestor de autenticación configurado.
        return config.getAuthenticationManager();
    }

    // Define el proveedor de autenticación que valida credenciales y utiliza el servicio de detalles del usuario.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Usa DaoAuthenticationProvider para cargar los detalles del usuario desde la base de datos.
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // Establece el servicio que carga usuarios.
        authenticationProvider.setUserDetailsService(userDetailService());
        // Configura el codificador de contraseñas para validar contraseñas correctamente.
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Proporciona un codificador de contraseñas seguro (BCrypt).
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Utiliza BCrypt para asegurar contraseñas con hashing.
        return new BCryptPasswordEncoder();
    }

    // Servicio que carga usuarios desde la base de datos utilizando el UserRepository.
    @Bean
    public UserDetailsService userDetailService() {
        // Implementa una lambda que busca usuarios por nombre de usuario.
        return username -> userRepository.findByUsername(username)
                // Lanza una excepción si el usuario no es encontrado.
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
