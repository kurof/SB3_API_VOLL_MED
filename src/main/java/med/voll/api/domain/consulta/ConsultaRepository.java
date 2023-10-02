package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

    //para buscar alguna cita del paciente
    Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, 
            LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    //buscando cita para el medico
    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);
    
}
