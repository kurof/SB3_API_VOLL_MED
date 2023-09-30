package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Entity
@Table(name = "pacientes")

//lombok
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String dni;
    private Boolean activo; //para activar pacientes

    @Embedded
    private Direccion direccion;


    //para el mapeo
    public Paciente(DatosRegistroPaciente datosRegistroPaciente){
        this.activo = true; //todos los elementos nuevos son true
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.telefono = datosRegistroPaciente.telefono();
        this.dni = datosRegistroPaciente.dni();
        this.direccion = datosRegistroPaciente.direccion();
    }


    public void actualizarDatos(@Valid DatosActualizarPaciente datosActualizarPaciente) {
        if(datosActualizarPaciente.nombre() != null){
            this.nombre = datosActualizarPaciente.nombre();
        }

        if(datosActualizarPaciente.telefono() != null){
            this.telefono = datosActualizarPaciente.telefono();
        }

        if(datosActualizarPaciente.email() != null){
            this.email = datosActualizarPaciente.email();
        }
    }


    public void desactivarPaciente(Paciente paciente) {
        this.activo = false;
    }
}
