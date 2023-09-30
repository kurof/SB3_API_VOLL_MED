package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendadoConsulta;

@RestController
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    //para inyectar el servicio
    @Autowired
    private AgendaDeConsultaService agendaServicio;
 
    @PostMapping
    @Transactional //en este lo pondre con el de spring a ver que pasa
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendadoConsulta datosAgendadoPaciente){

        agendaServicio.agendar(datosAgendadoPaciente); //para guardar y validar

        System.out.println(datosAgendadoPaciente);
        return ResponseEntity.ok(new DatosAgendadoConsulta(
            datosAgendadoPaciente.id(), datosAgendadoPaciente.idPaciente(), 
            datosAgendadoPaciente.idMedico(), datosAgendadoPaciente.fecha()));
    }
}
