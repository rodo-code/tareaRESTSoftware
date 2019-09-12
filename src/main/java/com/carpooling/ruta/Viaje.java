package com.carpooling.ruta;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
public class Viaje {
    private @Id @GeneratedValue Long id;
    private String descripcion;
    private Estado estado;

    Viaje(){}

    Viaje(String descripcion,Estado estado ){
        this.descripcion = descripcion;
        this.estado = estado;
    }
}
