package com.antecap.inscripciones.controller;

import com.antecap.inscripciones.dto.InscripcionDTO;
import com.antecap.inscripciones.model.Inscripcion;
import com.antecap.inscripciones.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Inscripcion> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Inscripcion> inscribir(@Valid @RequestBody InscripcionDTO dto) {
        Inscripcion nueva = service.crearInscripcion(dto.getCursoId(), dto.getParticipanteId());
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }
}