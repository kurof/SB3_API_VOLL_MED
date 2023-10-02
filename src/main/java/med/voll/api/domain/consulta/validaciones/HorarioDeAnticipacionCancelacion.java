package med.voll.api.domain.consulta.validaciones;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelacionConsulta;

@Component
public class HorarioDeAnticipacionCancelacion implements ValidacionCancelacionDeConsulta {
    
    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelacionConsulta datosCancelacionConsulta) {
        //para validar que la consulta se cancele con al menos 24 hrs de anticipacion
        
        var consulta = consultaRepository.getReferenceById(datosCancelacionConsulta.id());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if(diferenciaEnHoras < 24){
            throw new ValidationException("Cancelar consulta con al menos 24 hrs de anticipacion.");
        }
    }

}
