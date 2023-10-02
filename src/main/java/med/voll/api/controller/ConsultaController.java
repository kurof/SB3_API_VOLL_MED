package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendadoConsulta;
import med.voll.api.domain.consulta.DatosCancelacionConsulta;

@RestController
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key") //para OpenAPI
public class ConsultaController {

    //para inyectar el servicio
    @Autowired
    private AgendaDeConsultaService agendaServicio;

 
    @PostMapping
    @Transactional //en este lo pondre con el de spring a ver que pasa
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendadoConsulta datosAgendadoPaciente){

        var registro = agendaServicio.agendar(datosAgendadoPaciente); //para guardar y validar

        //probando el Git con VSCode
        //falta agregar el atributo especialidad
        System.out.println(datosAgendadoPaciente);
        return ResponseEntity.ok(registro);
    }

    //para borrar consultas
    @DeleteMapping
    @Transactional
    public ResponseEntity eliminarConsulta(@RequestBody @Valid DatosCancelacionConsulta datosCancelacion){
        agendaServicio.cancelar(datosCancelacion);
        return ResponseEntity.noContent().build();
    }
}
