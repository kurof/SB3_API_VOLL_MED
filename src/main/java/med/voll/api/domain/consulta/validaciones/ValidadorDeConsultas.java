package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendadoConsulta;

public interface ValidadorDeConsultas {
    
    //metodo abstracto que queremos que se implemente en las demas clases
    public void validar(DatosAgendadoConsulta datosAgendadoConsulta);
}
