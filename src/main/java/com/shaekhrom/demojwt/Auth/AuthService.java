package com.shaekhrom.demojwt.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shaekhrom.demojwt.Jwt.JwtService;
import com.shaekhrom.demojwt.User.Role;
import com.shaekhrom.demojwt.User.User;
import com.shaekhrom.demojwt.User.UserRepository;

import lombok.RequiredArgsConstructor;

// Marca esta clase como un servicio gestionado por Spring para contener lógica de negocio.
@Service
// Genera automáticamente un constructor para todos los campos marcados como 'final'.
@RequiredArgsConstructor
public class AuthService {

    // Repositorio para interactuar con la base de datos de usuarios.
    private final UserRepository userRepository;
    // Servicio personalizado para manejar JWT.
    private final JwtService jwtService;
    // Codificador de contraseñas para almacenar contraseñas de forma segura.
    private final PasswordEncoder passwordEncoder;
    // Maneja la autenticación de usuarios.
    private final AuthenticationManager authenticationManager;

    // Método para manejar el inicio de sesión de un usuario.
    public AuthResponse login(LoginRequest request) {
        // Valida las credenciales del usuario.
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Recupera los detalles del usuario de la base de datos.
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        // Genera un token JWT para el usuario autenticado.
        String token = jwtService.getToken(user);

        // Retorna una respuesta con el token generado.
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    // Método para manejar el registro de nuevos usuarios.
    public AuthResponse register(RegisterRequest request) {
        // Crea un nuevo usuario utilizando los datos recibidos.
        User user = User.builder()
                .username(request.getUsername()) // Asigna el nombre de usuario.
                .password(passwordEncoder.encode(request.getPassword())) // Codifica la contraseña.
                .firstname(request.getFirstname()) // Asigna el nombre.
                .lastname(request.lastname) // Asigna el apellido.
                .country(request.getCountry()) // Asigna el país.
                .role(Role.USER) // Asigna el rol predeterminado.
                .build();

        // Guarda el nuevo usuario en la base de datos.
        userRepository.save(user);

        // Retorna una respuesta con un token JWT para el usuario registrado.
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
