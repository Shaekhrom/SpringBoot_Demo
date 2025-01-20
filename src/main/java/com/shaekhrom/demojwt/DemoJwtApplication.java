package com.shaekhrom.demojwt;  // Paquete que contiene la clase principal de la aplicación.

import org.springframework.boot.SpringApplication;  // Importa la clase SpringApplication, que se usa para arrancar la aplicación.
import org.springframework.boot.autoconfigure.SpringBootApplication;  // Importa la anotación @SpringBootApplication que marca la clase principal de Spring Boot.

@SpringBootApplication  // Indica que esta clase es la configuración principal de la aplicación Spring Boot.
public class DemoJwtApplication {  // Define la clase principal de la aplicación.

	// El punto de entrada principal de la aplicación
	public static void main(String[] args) {
		// Arranca la aplicación Spring Boot usando SpringApplication.run()
		// Esto configura el contexto de Spring, inicia el servidor embebido (por ejemplo, Tomcat)
		// y se encarga de todo el proceso de inicio.
		SpringApplication.run(DemoJwtApplication.class, args);  // Llama a run() para iniciar la aplicación.
	}
}
