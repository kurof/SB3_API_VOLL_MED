package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //para indicar que es una clase de configuracion
@EnableWebSecurity //habilitar modulo web security
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter; //instancia del filtro que creamos

    @Bean //para que jale e indique que se crearan los beans de seguridad
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //se indica el tipo de sesion que tendra la api
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(management -> 
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/login")
                        .permitAll() //para permitir los request en el login y generar token
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**")
                        .permitAll()
                        // .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
                        .anyRequest().authenticated()) //autenticar request que NO van a login
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) 
                //para que se ejecute nuestro filtro antes
                .build();
                //hay demasiados metodos deprecated aqui
                //PERO podemos corregirlos pasando a la sintaxis lambda
    }  

    //para pasar el authentication manager
    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //para trabajar con usuarios y contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
