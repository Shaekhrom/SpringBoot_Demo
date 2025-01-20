package com.shaekhrom.demojwt.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController // Indica que esta clase es un controlador REST y devuelve respuestas en formato JSON.
@RequestMapping("/auth") // Define el prefijo común para todas las rutas de este controlador como "/auth".
@RequiredArgsConstructor // Genera automáticamente un constructor con los campos marcados como 'final'.
public class AuthController {

    private final AuthService authService; // Servicio que contiene la lógica de negocio para autenticación y registro.

    @PostMapping(value = "login") // Define un endpoint POST en la ruta "/auth/login".
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Llama al servicio de autenticación para procesar el inicio de sesión y devuelve la respuesta.
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register") // Define un endpoint POST en la ruta "/auth/register".
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Llama al servicio de autenticación para registrar un nuevo usuario y devuelve la respuesta.
        return ResponseEntity.ok(authService.register(request));
    }
}
