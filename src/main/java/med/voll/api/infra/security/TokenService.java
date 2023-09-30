package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import med.voll.api.domain.usuarios.Usuario;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret; //para consumir el valor secret en application.properties
    
    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create() //quitamos la creacion de la variable
                .withIssuer("voll med")
                .withSubject(usuario.getLogin())
                .withClaim("id", usuario.getId())
                .withExpiresAt(generarFechaExpiracion())
                .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }

    //metodo para consumir y validar el token
    public String getSubject(String token){
        
        //verificar que le token contenga algo
        if(token == null){
            throw new RuntimeException();
        }

        DecodedJWT verifier = null; //poniendo el verificador fuera
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); //validando firma
            verifier = JWT.require(algorithm)
                .withIssuer("voll med")
                .build()
                .verify(token);   
            verifier.getSubject();             
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }

        // assert verifier != null; //que el elemento no sea nulo
        if(verifier.getSubject() == null){
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    //metodo para crear el instant para poner tiempo de validez
    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }
}
