package com.shaekhrom.demojwt.Demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor; // Anotación de Lombok para generar el constructor.

@RestController // Anotación que convierte la clase en un controlador REST, indicando que las respuestas serán JSON o similares.
@RequestMapping("/api/v1") // Define la ruta base para las solicitudes dentro de este controlador, en este caso "/api/v1".
@RequiredArgsConstructor // Lombok generará automáticamente un constructor con todos los campos finales requeridos.

public class DemoController { // Clase controladora, que manejará las solicitudes de la API.

    @PostMapping(value = "demo") // Define que este método manejará las solicitudes POST a la ruta "/api/v1/demo".
    public String welcome() { // Método que se ejecutará cuando se reciba una solicitud POST en la ruta indicada.
        return "Welcome from secure endpoint"; // Respuesta que se enviará al cliente, en este caso un simple mensaje de bienvenida.
    }
}
