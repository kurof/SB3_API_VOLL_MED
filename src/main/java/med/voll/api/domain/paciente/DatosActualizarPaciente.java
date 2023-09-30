package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarPaciente(@NotNull Long id, String nombre, String telefono, 
            String email) {
    //no pondre la direccion porque no me parece seguro
}
