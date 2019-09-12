package com.carpooling.ruta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MensajeViajeNoEncontrado {
    @ResponseBody
    @ExceptionHandler(ExcepcionViajeNoEncontrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String viajeNoEncontradaCabezera(ExcepcionViajeNoEncontrado ex){
        return ex.getMessage();
    }
}
