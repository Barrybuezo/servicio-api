package com.antecap.inscripciones.service;

import com.antecap.inscripciones.enums.EstadoInscripcion;
import com.antecap.inscripciones.enums.Modalidad;
import com.antecap.inscripciones.exception.ResourceNotFoundException;
import com.antecap.inscripciones.model.Curso;
import com.antecap.inscripciones.model.Inscripcion;
import com.antecap.inscripciones.model.Participante;
import com.antecap.inscripciones.repository.CursoRepository;
import com.antecap.inscripciones.repository.InscripcionRepository;
import com.antecap.inscripciones.repository.ParticipanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private ParticipanteRepository participanteRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    private Curso cursoPrueba;
    private Participante participantePrueba;

    @BeforeEach
    void setUp() {
        cursoPrueba = new Curso();
        cursoPrueba.setId(1L); // ID asignado para evitar NullPointerException
        cursoPrueba.setCodigo("JAVA-25");
        cursoPrueba.setNombre("Spring Boot 3");
        cursoPrueba.setCupo(2);
        cursoPrueba.setActivo(true);
        cursoPrueba.setModalidad(Modalidad.VIRTUAL);

        participantePrueba = new Participante();
        participantePrueba.setId(1L); // ID asignado para evitar NullPointerException
        participantePrueba.setNombres("Víctor");
        participantePrueba.setApellidos("García");
        participantePrueba.setCorreo("victor@antecap.com");
    }

    @Test
    void testCrearInscripcionExitosa() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(cursoPrueba));
        when(participanteRepository.findById(1L)).thenReturn(Optional.of(participantePrueba));
        when(inscripcionRepository.findAll()).thenReturn(Collections.emptyList());
        when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Inscripcion resultado = inscripcionService.crearInscripcion(1L, 1L);

        assertNotNull(resultado);
        assertEquals(EstadoInscripcion.ACTIVA, resultado.getEstado());
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
    }

    @Test
    void testCrearInscripcionFallaSinCupo() {
        cursoPrueba.setCupo(1);

        Inscripcion inscripcionExistente = new Inscripcion();
        inscripcionExistente.setCurso(cursoPrueba);
        inscripcionExistente.setParticipante(participantePrueba);
        inscripcionExistente.setEstado(EstadoInscripcion.ACTIVA);

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(cursoPrueba));
        when(participanteRepository.findById(1L)).thenReturn(Optional.of(participantePrueba));
        when(inscripcionRepository.findAll()).thenReturn(List.of(inscripcionExistente));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inscripcionService.crearInscripcion(1L, 1L)
        );

        assertTrue(exception.getMessage().contains("cupo máximo"));
    }

    @Test
    void testCrearInscripcionFallaDuplicada() {
        Inscripcion inscripcionExistente = new Inscripcion();
        inscripcionExistente.setCurso(cursoPrueba);
        inscripcionExistente.setParticipante(participantePrueba);
        inscripcionExistente.setEstado(EstadoInscripcion.ACTIVA);

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(cursoPrueba));
        when(participanteRepository.findById(1L)).thenReturn(Optional.of(participantePrueba));
        when(inscripcionRepository.findAll()).thenReturn(List.of(inscripcionExistente));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inscripcionService.crearInscripcion(1L, 1L)
        );

        assertTrue(exception.getMessage().contains("ya se encuentra inscrito"));
    }

    @Test
    void testCrearInscripcionFallaCursoNoEncontrado() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> inscripcionService.crearInscripcion(99L, 1L)
        );
    }
}