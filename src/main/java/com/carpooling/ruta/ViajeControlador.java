package com.carpooling.ruta;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
@RestController
public class ViajeControlador {
    private final RepositorioViaje repositorioViaje;
    private final EnsambladorResourceViaje ensambladorResourceViaje;
    ViajeControlador(RepositorioViaje repositorioViaje,EnsambladorResourceViaje ensambladorResourceViaje){
        this.repositorioViaje = repositorioViaje;
        this.ensambladorResourceViaje = ensambladorResourceViaje;
    }

    @GetMapping("/viajes")
    Resources<Resource<Viaje>> todo(){
        List<Resource<Viaje>> viajes = repositorioViaje.findAll().stream()
                .map(ensambladorResourceViaje::toResource)
                .collect(Collectors.toList());
        return new Resources<>(viajes,
                linkTo(methodOn(ViajeControlador.class).todo()).withSelfRel());
    }

    @GetMapping("/viajes/{id}")
    Resource<Viaje> uno(@PathVariable Long id){
        return ensambladorResourceViaje.toResource(
                repositorioViaje.findById(id)
                .orElseThrow(()->new ExcepcionViajeNoEncontrado(id))
        );
    }

    @PostMapping("/viajes")
    ResponseEntity<Resource<Viaje>> nuevoViaje(@RequestBody Viaje viaje){
        viaje.setEstado(Estado.PEDIDO);
        Viaje nuevoviaje = repositorioViaje.save(viaje);
        return ResponseEntity.created(linkTo(methodOn(ViajeControlador.class).uno(nuevoviaje.getId())).toUri())
                .body(ensambladorResourceViaje.toResource(nuevoviaje));
    }

    @DeleteMapping("/viajes/{id}/cancel")
    ResponseEntity<ResourceSupport> cancelar(@PathVariable Long id) {

        Viaje viaje = repositorioViaje.findById(id).orElseThrow(() -> new ExcepcionViajeNoEncontrado(id));

        if (viaje.getEstado() == Estado.PEDIDO) {
            viaje.setEstado(Estado.CANCELADO);
            return ResponseEntity.ok(ensambladorResourceViaje.toResource(repositorioViaje.save(viaje)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Metodo no disponible", "No puedes cancelar el viaje en  " + viaje.getEstado() + " estado"));
    }

    @PutMapping("/viajes/{id}/complete")
    ResponseEntity<ResourceSupport> completar(@PathVariable Long id){
        Viaje viaje = repositorioViaje.findById(id).orElseThrow(()->new ExcepcionViajeNoEncontrado(id));
        if(viaje.getEstado() == Estado.PEDIDO){
            viaje.setEstado(Estado.COMPLETADO);
            return ResponseEntity.ok(ensambladorResourceViaje.toResource(repositorioViaje.save(viaje)));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Metodo no disponible", "No puedes completar el viaje en  " + viaje.getEstado() + " estado"));
    }
}
