package med.voll.api.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException{

    public ValidacionDeIntegridad(String mensajeDeError){
        super(mensajeDeError);
    }
}
