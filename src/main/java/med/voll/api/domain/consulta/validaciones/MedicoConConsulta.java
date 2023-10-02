package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendadoConsulta;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {
    
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosAgendadoConsulta datosAgendadoConsulta){

        //viendo que no sea nulo
        if(datosAgendadoConsulta.idMedico() == null){
            return;
        }

        var medicoConConsulta = consultaRepository.existsByMedicoIdAndFecha(
            datosAgendadoConsulta.idMedico(), datosAgendadoConsulta.fecha()
        ); //regresa true si existe, false si no

        if(medicoConConsulta){ //si devuelve true
            throw new ValidationException("El medico ya tiene una cita en ese horario.");
        }
    }
}
