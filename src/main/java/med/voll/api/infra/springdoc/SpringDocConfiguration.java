package med.voll.api.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

    //para agregar el token y podamos usar swagger y openapi
    @Bean
    public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .components(new Components()
            .addSecuritySchemes("bearer-key",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            //agregando mas informacion
            .info(new Info().title("API voll med")
                    .description("API Rest de la aplicacion voll med, contiene funcionalidades CRUD" + 
                    "para medicos, pacientes y consultas")
                    .contact(new Contact().name("Equipo BackEnd")
                            .email("backend@voll.med"))
            .license(new License().name("Apache 2.0")
                    .url("http://voll.med/api/licencia")));
    }

    @Bean
    public void mensaje(){
        System.out.println("Bearer esta funcionando.");
    }
}
