package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    //para la autorizacion
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //obtenemos el token contenido en el header del request
        var authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);

        //verificar que el token NO este nulo
        if(authHeader != null){
            var token = authHeader.replace("Bearer ", ""); //si no esta vacio se reemplaza
            var nombreUsuario = tokenService.getSubject(token);

            if(nombreUsuario != null){
                //token valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var autenticacion = new UsernamePasswordAuthenticationToken(usuario, null,
                                    usuario.getAuthorities()); //forzando inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        }
        //para poder llegar al controller y nos devuelva los datos
        filterChain.doFilter(request, response);
    }  
}
