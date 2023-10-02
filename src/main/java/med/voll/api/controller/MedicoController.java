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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.DatosActualizarMedico;
import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key") //para OpenAPI
public class MedicoController {
    
    //llamando al repositorio
    @Autowired
    private MedicoRepository medicoRepository;

    //para registrar
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico>registrarMedico(@RequestBody @Valid 
                            DatosRegistroMedico datosRegistroMedico,
                            UriComponentsBuilder uriComponentsBuilder){        
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));

        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
            medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(),
            medico.getEspecialidad().toString(),
            medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), 
            medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), 
            medico.getDireccion().getComplemento()
        ));
        //para crear nuestra url, dios esta cosa estuvo dificil
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    //para listar
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) 
            Pageable paginacion){
        // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    //para actualizar
    @PutMapping
    @Transactional //para hacer y cerrar la transaccion
    public ResponseEntity <DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid 
    DatosActualizarMedico datosActualizarMedico){

        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        //para regresar algo en el codigo HTTP del request
        return ResponseEntity.ok(new DatosRespuestaMedico(
            medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(),
            medico.getEspecialidad().toString(),
            medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), 
            medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), 
            medico.getDireccion().getComplemento())
        )); 
    }

    //para eliminar registros (bueno excluirlos)
    @DeleteMapping("/{id}")
    @Transactional
    //DELETE LOGICO
    public ResponseEntity eliminarMedico (@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    //para obtener un elemento
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico>retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedicos = new DatosRespuestaMedico(
            medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(),
            medico.getEspecialidad().toString(),
            medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), 
            medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), 
            medico.getDireccion().getComplemento())
        );
        return ResponseEntity.ok(datosMedicos);
    }

    //DELETE PURO EN LA BASE DE DATOS, BORRAR pues
/*     public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    } */

}  
