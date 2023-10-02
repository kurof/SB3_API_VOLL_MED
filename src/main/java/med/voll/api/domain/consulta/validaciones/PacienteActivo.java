package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendadoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

@Component
public class PacienteActivo implements ValidadorDeConsultas{
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    public void validar(DatosAgendadoConsulta datosAgendadoConsulta){

        if(datosAgendadoConsulta.idPaciente() == null){
            return;
        }

        var pacienteActivo = pacienteRepository.
                            findActivoById(datosAgendadoConsulta.idPaciente());
        //si el paciente esta inactivo en la DB
        if(!pacienteActivo){ //parece que asi se busca un elemento false
            throw new ValidationException
                    ("El paciente no se encuentra activo en el sistema.");
        }
    }
}
