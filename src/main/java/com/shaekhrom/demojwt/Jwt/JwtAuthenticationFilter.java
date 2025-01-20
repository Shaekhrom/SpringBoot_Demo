package com.shaekhrom.demojwt.Jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// Marca la clase como un componente de Spring.
@Component

// Genera un constructor que inyecta automáticamente las dependencias finales.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Servicio para operaciones relacionadas con JWT.
    private final JwtService jwtService;

    // Servicio para cargar detalles del usuario desde el repositorio.
    private final UserDetailsService userDetailsService;

    // Método principal del filtro, ejecutado en cada solicitud.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtiene el token JWT de la solicitud.
        final String token = getTokenFromRequest(request);
        final String username;

        // Si no hay token, continúa con el siguiente filtro.
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrae el nombre de usuario del token.
        username = jwtService.getUsernameFromToken(token);

        // Si el usuario no está autenticado actualmente.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carga los detalles del usuario desde la base de datos.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Si el token es válido.
            if (jwtService.isTokenValid(token, userDetails)) {
                // Crea un objeto de autenticación con los detalles del usuario.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // Configura detalles adicionales para la autenticación.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto de seguridad de Spring.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continúa con el siguiente filtro en la cadena.
        filterChain.doFilter(request, response);
    }

    // Método para extraer el token JWT de la cabecera de la solicitud.
    private String getTokenFromRequest(HttpServletRequest request) {
        // Obtiene el valor de la cabecera "Authorization".
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Comprueba que la cabecera no esté vacía y comience con "Bearer ".
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            // Devuelve el token sin el prefijo "Bearer ".
            return authHeader.substring(7);
        }
        // Si no hay cabecera válida, retorna null.
        return null;
    }
}
