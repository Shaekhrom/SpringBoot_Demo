package com.shaekhrom.demojwt.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Genera automáticamente getters, setters, toString, equals y hashCode.
@Data
// Proporciona un patrón Builder para crear instancias de esta clase de forma más fluida.
@Builder
// Genera un constructor que toma todos los campos como parámetros.
@AllArgsConstructor
// Genera un constructor vacío (sin parámetros), útil para deserialización JSON.
@NoArgsConstructor
public class LoginRequest {
    // Nombre de usuario enviado por el cliente para iniciar sesión.
    String username;
    // Contraseña enviada por el cliente para autenticación.
    String password;
}
