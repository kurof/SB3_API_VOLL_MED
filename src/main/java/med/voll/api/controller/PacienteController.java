package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.var;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosActualizarPaciente;
import med.voll.api.domain.paciente.DatosListadoPaciente;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.DatosRespuestaPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    //para el registro de pacientes
    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPacientes(@RequestBody @Valid 
    DatosRegistroPaciente datosRegistroPaciente, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));

        DatosRespuestaPaciente datosPaciente = new DatosRespuestaPaciente(
            paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),
            paciente.getDni(), new DatosDireccion(paciente.getDireccion().getCalle(),
            paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
            paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento())
        );

        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosPaciente);
    }

    //para el listado de pacientes
    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listarPacientes
        (@PageableDefault(size = 4, page = 0, sort = {"nombre"}) Pageable paginacion){
        //cambiando para que se adapte a la parte de eliminar
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).
        map(DatosListadoPaciente::new));
    }
    
    //para actualizar
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid 
    DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);

        var datosPaciente = new DatosRespuestaPaciente(
            paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),
            paciente.getDni(), new DatosDireccion(paciente.getDireccion().getCalle(),
            paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
            paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento())
        );

        return ResponseEntity.ok(datosPaciente);
    }

    //para borrar de forma logica (solo desactivando)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente(paciente);
        return ResponseEntity.noContent().build();
    }

    //para obtener un solo elemento
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornarDatosPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(
            paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),
            paciente.getDni(), new DatosDireccion(paciente.getDireccion().getCalle(),
            paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
            paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento())
        );
        return ResponseEntity.ok(datosPaciente);
    }
}
