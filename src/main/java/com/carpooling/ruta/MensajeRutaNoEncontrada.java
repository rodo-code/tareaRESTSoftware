package com.carpooling.ruta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MensajeRutaNoEncontrada {

    @ResponseBody
    @ExceptionHandler(ExcepcionRutaNoEncontrada.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String rutaNoEncontradaCabezera(ExcepcionRutaNoEncontrada ex){
        return ex.getMessage();
    }
}
