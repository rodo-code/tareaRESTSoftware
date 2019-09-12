package com.carpooling.ruta;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class EnsambladorResourceRuta implements ResourceAssembler<Ruta, Resource<Ruta>> {
    @Override
    public Resource<Ruta> toResource(Ruta ruta){
        return new Resource<>(ruta,
                linkTo(methodOn(RutaControlador.class).uno(ruta.getId())).withSelfRel(),
                linkTo(methodOn(RutaControlador.class).todo()).withRel("rutas"));
    }

}
