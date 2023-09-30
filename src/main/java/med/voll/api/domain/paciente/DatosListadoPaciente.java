package med.voll.api.domain.paciente;

public record DatosListadoPaciente(Long id, String nombre, String email, String telefono, String dni) {
    
    public DatosListadoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), 
            paciente.getTelefono(), paciente.getDni());
    }
}
