package med.voll.api.infra.errores;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GestionDeErrores {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionarError404(){
        return ResponseEntity.notFound().build(); //no olvidar el build

        //cuando se lance una exception del tipo EntityNotFound
        //devolera un codigo 404
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosDeErrorRegistro::new).toList(); 
        //pasamos la lista de errores a una variable
        return ResponseEntity.badRequest().body(errores); //regresamos la lista de errores corta
    }

    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity errorValidacionesDeIntegridad(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());

    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorValidacionesDeNegocio(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    //DTO a nivel interno para obtener la lista de errores
    private record DatosDeErrorRegistro(String campo, String error) {
        public DatosDeErrorRegistro (FieldError error){
            this(error.getField(), error.getDefaultMessage());
            //mapeando el campo y el mensaje del error
        }
    }
}
