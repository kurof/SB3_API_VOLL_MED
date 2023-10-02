package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelacionConsulta(Long id, @NotNull MotivoCancelacion motivo) {
    //la cancelacion parece que solo requiere de 2 elementos: id de consulta y el motivo
}
