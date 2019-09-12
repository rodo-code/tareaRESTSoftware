package com.carpooling.ruta;

public class ExcepcionViajeNoEncontrado extends RuntimeException {
    ExcepcionViajeNoEncontrado(Long id){
        super("No se pudo encontrar el viaje "+id);
    }
}
