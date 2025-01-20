package com.shaekhrom.demojwt.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Genera automáticamente getters, setters, toString, equals y hashCode.
@Data
// Proporciona un patrón Builder para crear instancias de esta clase de manera más fluida.
@Builder
// Genera un constructor vacío (sin parámetros), útil para deserialización JSON.
@NoArgsConstructor
// Genera un constructor con todos los campos como parámetros.
@AllArgsConstructor
public class RegisterRequest {
    // Nombre de usuario proporcionado por el cliente para registro.
    String username;
    // Contraseña proporcionada por el cliente que será codificada.
    String password;
    // Nombre del usuario.
    String firstname;
    // Apellido del usuario.
    String lastname;
    // País del usuario.
    String country;
}
