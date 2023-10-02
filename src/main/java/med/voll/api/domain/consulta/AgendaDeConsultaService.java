package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.validaciones.ValidacionCancelacionDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;

@Service
public class AgendaDeConsultaService {

    //para buscar los datos de Medico y Paciente
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private ConsultaRepository consultaRepository;

    //para usar las validaciones, el autowired nos ayudara
    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    List<ValidacionCancelacionDeConsulta> validadorCancelacion;

    //para guardar los datos de agendado
    public DatosDetalleConsulta agendar(DatosAgendadoConsulta datosAgendadoConsulta){

        //VALIDACIONES
        if(!pacienteRepository.findById(datosAgendadoConsulta.idPaciente()).isPresent()){
            //si el elemento paciente con el ID "x" NO esta en la DB:
            throw new ValidacionDeIntegridad("El ID del paciente no fue encontrado.");
        }

        if(datosAgendadoConsulta.idMedico() != null
            && !medicoRepository.existsById(datosAgendadoConsulta.idMedico())){
            //si el elemento medico con el ID "x" NO esta en la DB
            throw new ValidacionDeIntegridad("El ID del medico no fue encontrado.");
        }
        

        //VALIDACIONES COMPLEJAS ACORDE A LAS REGLAS DEL NEGOCIO
        validadores.forEach(v -> v.validar(datosAgendadoConsulta));

        //tomamos los datos del medico y paciente que hay en la base de datos
        //el get() es para que sean variables de la Entidad que estamos buscando (Medico o Paciente)
        var medico = seleccionarMedico(datosAgendadoConsulta);
        if(medico == null){
            //validando si el medico ha llegado vacio
            throw new 
                ValidacionDeIntegridad("No hay medicos disponibles para este horario y especialidad.");
        }

        var paciente = pacienteRepository.findById(datosAgendadoConsulta.idPaciente()).get();

        var consulta = new Consulta(medico, paciente, datosAgendadoConsulta.fecha());
        consultaRepository.save(consulta); //para guardar la consulta

        return new DatosDetalleConsulta(consulta);
    }

    //metodo para seleccionar medico en caso de que no se envie nada por el cliente
    private Medico seleccionarMedico(DatosAgendadoConsulta datosAgendadoConsulta) {

        //validaciones, la especialidad es en caso de que no haya ID del medico
        if(datosAgendadoConsulta.idMedico() != null){
            //si el elemento NO es nulo devolvemos el medico
            return medicoRepository.getReferenceById(datosAgendadoConsulta.idMedico());
        }
        if(datosAgendadoConsulta.especialidad() == null){
            //si la especialidad es NULA se manda un mensaje de error
            throw new ValidacionDeIntegridad
                    ("Debe seleccionar una especialidad para el medico.");
        }

        //para devolver el medico de forma aleatoria
        return medicoRepository.seleccionarMedicoConEspecialidadParaAgendar(
            datosAgendadoConsulta.especialidad(), datosAgendadoConsulta.fecha()
        );
    }


    //Para cancelas las consultas
    public void cancelar(@Valid DatosCancelacionConsulta datosCancelacion) {
        if(!consultaRepository.existsById(datosCancelacion.id())){
            throw new ValidacionDeIntegridad("El ID de la consulta no existe.");
        }

        validadorCancelacion.forEach(v -> v.validar(datosCancelacion));

        var consulta = consultaRepository.getReferenceById(datosCancelacion.id());
        consulta.cancelar(datosCancelacion.motivo());
    }
}
