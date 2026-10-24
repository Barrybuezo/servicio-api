package com.antecap.inscripciones.service;

import com.antecap.inscripciones.enums.EstadoInscripcion;
import com.antecap.inscripciones.exception.ResourceNotFoundException;
import com.antecap.inscripciones.model.Curso;
import com.antecap.inscripciones.model.Inscripcion;
import com.antecap.inscripciones.model.Participante;
import com.antecap.inscripciones.repository.CursoRepository;
import com.antecap.inscripciones.repository.InscripcionRepository;
import com.antecap.inscripciones.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final CursoRepository cursoRepository;
    private final ParticipanteRepository participanteRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository,
                              CursoRepository cursoRepository,
                              ParticipanteRepository participanteRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.cursoRepository = cursoRepository;
        this.participanteRepository = participanteRepository;
    }

    public List<Inscripcion> listarTodas() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion crearInscripcion(Long cursoId, Long participanteId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + cursoId));

        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new ResourceNotFoundException("Participante no encontrado con ID: " + participanteId));

        //Solo se inscribe en cursos activos
        if (Boolean.FALSE.equals(curso.getActivo())) {
            throw new IllegalArgumentException("No se puede inscribir en un curso inactivo");
        }

        //Impedir inscripción sin cupo
        long inscritosActivos = inscripcionRepository.findAll().stream()
                .filter(i -> i.getCurso().getId().equals(cursoId) && i.getEstado() == EstadoInscripcion.ACTIVA)
                .count();

        if (inscritosActivos >= curso.getCupo()) {
            throw new IllegalArgumentException("El curso ha alcanzado su cupo máximo de " + curso.getCupo() + " participantes");
        }

        // Un participante no puede inscribirse dos veces al mismo curso
        boolean yaInscrito = inscripcionRepository.findAll().stream()
                .anyMatch(i -> i.getCurso().getId().equals(cursoId)
                        && i.getParticipante().getId().equals(participanteId)
                        && i.getEstado() == EstadoInscripcion.ACTIVA);

        if (yaInscrito) {
            throw new IllegalArgumentException("El participante ya se encuentra inscrito en este curso");
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCurso(curso);
        inscripcion.setParticipante(participante);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setEstado(EstadoInscripcion.ACTIVA);

        return inscripcionRepository.save(inscripcion);
    }

    // Cancelar libera el lugar del cupo
    public void cancelarInscripcion(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con ID: " + id));

        inscripcion.setEstado(EstadoInscripcion.CANCELADA);
        inscripcionRepository.save(inscripcion);
    }
}