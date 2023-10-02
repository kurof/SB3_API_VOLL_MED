package med.voll.api.domain.consulta.validaciones;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendadoConsulta;

@Component
public class HorarioDeAtencionClinica implements ValidadorDeConsultas {
    
    public void validar(DatosAgendadoConsulta datosAgendadoConsulta){

        //comparando la fecha que se recibe del cliente y
        //viendo si cae en domingo
        var domingo = DayOfWeek.SUNDAY.equals(datosAgendadoConsulta.fecha().getDayOfWeek());
        var antesDeApertura = datosAgendadoConsulta.fecha().getHour() < 7; //AM
        var despuesDeCierre = datosAgendadoConsulta.fecha().getHour() > 19; //PM

        if(domingo || antesDeApertura || despuesDeCierre){
            throw new ValidationException
                ("El horario de atencion es de 7AM a 7PM, de lunes a sabado");
        }
    }
}
