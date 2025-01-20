package com.shaekhrom.demojwt.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Combina varias anotaciones: genera getter y setter, equals, hashCode y toString automáticamente.
@Builder // Permite la construcción de objetos de esta clase usando el patrón Builder.
@NoArgsConstructor // Genera un constructor sin parámetros.
@AllArgsConstructor // Genera un constructor con parámetros para todos los campos de la clase.
public class AuthResponse {
    String token; // Campo que contiene el token de autenticación (posiblemente un JWT).
}







