package com.carpooling.ruta;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
@Component
public class EnsambladorResourceViaje implements ResourceAssembler<Viaje, Resource<Viaje>> {
    @Override
    public Resource<Viaje> toResource(Viaje viaje){
        Resource<Viaje> viajeResource = new Resource<>(viaje,
                linkTo(methodOn(ViajeControlador.class).uno(viaje.getId())).withSelfRel(),
                linkTo(methodOn(ViajeControlador.class).todo()).withRel("viajes"));

        if(viaje.getEstado() == Estado.PEDIDO){
            viajeResource.add(
                    linkTo(methodOn(ViajeControlador.class).cancelar(viaje.getId())).withRel("cancelado"));
            viajeResource.add(
                    linkTo(methodOn(ViajeControlador.class).completar(viaje.getId())).withRel("completado")
            );
        }
        return viajeResource;
    }
}
