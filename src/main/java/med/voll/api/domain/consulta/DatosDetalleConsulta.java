package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import med.voll.api.domain.medico.Especialidad;

public record DatosDetalleConsulta(Long id, Long idPaciente, Long idMedico, 
            LocalDateTime fecha, Especialidad especialidad) {
    
    //para regresar los detalles que ingrese el cliente
    public DatosDetalleConsulta(Consulta consulta){
        this(
            consulta.getId(), consulta.getPaciente().getId(),
            consulta.getMedico().getId(), consulta.getFecha(),
            consulta.getEspecialidad()
        );
    }
}
