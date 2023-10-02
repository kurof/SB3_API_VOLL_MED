package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendadoConsulta;
import med.voll.api.domain.medico.MedicoRepository;

@Component
public class MedicoActivo implements ValidadorDeConsultas {
    
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosAgendadoConsulta datosAgendadoConsulta){

        //verificar si el Id del medico esta nulo o no
        if(datosAgendadoConsulta.idMedico() == null){
            return;
        }

        //tomando a un medico activo en el sistema
        var medicoActivo = medicoRepository.findActivoById(datosAgendadoConsulta.idMedico());
        
        //si el elemento NO esta activo, esto se hace con el "!"
        if(!medicoActivo){
            throw new ValidationException("El Medico no se encuentra activo en el sistema.");
        }
    }
}
