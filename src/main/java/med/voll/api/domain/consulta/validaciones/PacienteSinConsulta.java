package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendadoConsulta;

@Component //de aqui surgen los controllers, repository, etc.
public class PacienteSinConsulta implements ValidadorDeConsultas {
    
    @Autowired
    private ConsultaRepository consultaRepository;

    //para ver si un paciente ya tiene una cita para el dia
    //NO podra hacer otra cita para el mismo dia
    public void validar(DatosAgendadoConsulta datosAgendadoConsulta){

        var primerHorario = datosAgendadoConsulta.fecha().withHour(7);
        var ultimoHorario = datosAgendadoConsulta.fecha().withHour(18);

        var pacienteConCita = consultaRepository.
                existsByPacienteIdAndFechaBetween(datosAgendadoConsulta.idPaciente(),
                primerHorario, ultimoHorario);
        
                //si existe el paciente con una cita y se intenta hacer otra el mismo dia
                if(pacienteConCita){
                    throw new ValidationException(
                        "El paciente ya tiene una cita existente en ese dia.");
                }
    }
}
