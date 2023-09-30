package med.voll.api.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Entity //creo que es redundante poner el nombre Medico
@Table(name = "medicos")

//Lombok para generar codigo
@NoArgsConstructor //constructor sin atributos
@AllArgsConstructor //constructor con atributos
@Getter
@EqualsAndHashCode(of = "id") //usar parametro id para comparar
public class Medico {

    //atributos de la entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo; //para distinguir registros activos

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true; //todo medico creado sera activo
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.telefono = datosRegistroMedico.telefono();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {

        //para ver si actualiza uno o todos los datos
        if(datosActualizarMedico.nombre() != null){
            this.nombre = datosActualizarMedico.nombre();
        }

        if(datosActualizarMedico.documento() != null){
            this.documento = datosActualizarMedico.documento();
        }

        if(datosActualizarMedico.direccion() != null){
            this.direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());
        }
    }

    //para desactivar elementos en la base de datos
    public void desactivarMedico() {
        this.activo = false;
    }
}
