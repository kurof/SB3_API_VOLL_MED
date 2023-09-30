package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendadoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

public class PacienteActivo {
    
    private PacienteRepository pacienteRepository;
    
    public void validar(DatosAgendadoConsulta datosAgendadoConsulta){

        if(datosAgendadoConsulta.idPaciente() == null){
            return;
        }
    }
}
