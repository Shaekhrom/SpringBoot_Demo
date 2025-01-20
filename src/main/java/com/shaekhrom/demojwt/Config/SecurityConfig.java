package com.shaekhrom.demojwt.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shaekhrom.demojwt.Jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// Marca esta clase como una configuración de Spring para definir la seguridad web.
@Configuration
// Activa la configuración de seguridad personalizada para la aplicación.
@EnableWebSecurity
// Genera un constructor que inyecta automáticamente las dependencias finales.
@RequiredArgsConstructor
public class SecurityConfig {

    // Filtro personalizado para manejar la autenticación basada en tokens JWT.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Proveedor de autenticación, previamente configurado para manejar validaciones.
    private final AuthenticationProvider authProvider;

    // Define la configuración de seguridad web mediante una cadena de filtros.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desactiva la protección CSRF porque JWT ya protege contra este tipo de ataques.
                .csrf(csrf -> csrf.disable())

                // Configura las políticas de autorización para las rutas.
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                // Permite acceso sin autenticación a rutas bajo "/auth/**".
                                .requestMatchers("/auth/**").permitAll()
                                // Requiere autenticación para cualquier otra ruta.
                                .anyRequest().authenticated()
                )

                // Configura la gestión de sesiones como sin estado (stateless).
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Establece el proveedor de autenticación que manejará las validaciones.
                .authenticationProvider(authProvider)

                // Agrega un filtro antes del filtro predeterminado para manejar tokens JWT.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // Construye y retorna la cadena de filtros configurada.
                .build();
    }
}
