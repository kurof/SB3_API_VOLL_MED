package med.voll.api.domain.consulta.validaciones;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendadoConsulta;

public class HorarioDeAnticipacion {
    
    public void validar(DatosAgendadoConsulta datosAgendadoConsulta){

        var ahora = LocalDateTime.now(); //para obtener la hora actual
        var horaDeAgendado = datosAgendadoConsulta.fecha();

        var diferenciaDe30Min = Duration.between(ahora, horaDeAgendado).toMinutes() < 30;

        if(diferenciaDe30Min){ //si la diferencia es menor a 30 min
            throw new ValidationException("Las citas deben hacerse con al menos 30 min de anticipacion.");
        }
    }
}
