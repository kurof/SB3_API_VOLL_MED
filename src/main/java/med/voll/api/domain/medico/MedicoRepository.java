package med.voll.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findByActivoTrue(Pageable paginacion);

    //haciendo query para la este metodo
    @Query("""
            SELECT m FROM Medico m
            WHERE m.activo = TRUE AND
            m.especialidad = :especialidad AND
            m.id NOT IN(
                SELECT c.medico.id FROM Consulta c
                WHERE c.fecha = :fecha
            )

            ORDER BY RAND()
            LIMIT 1
            """)
    Medico seleccionarMedicoConEspecialidadParaAgendar(Especialidad especialidad, LocalDateTime fecha);

    //devolviendo el elemento activo a traves del id
    @Query("""
            SELECT m.activo
            FROM Medico m
            WHERE m.id = :idMedico
            """)
    Boolean findActivoById(Long idMedico);    
}
