package com.antecap.inscripciones.dto;

import com.antecap.inscripciones.enums.EstadoInscripcion;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class InscripcionDTO {
    private Long id;

    @NotNull(message = "El ID del curso es obligatorio")
    private Long cursoId;

    @NotNull(message = "El ID del participante es obligatorio")
    private Long participanteId;

    private LocalDate fechaInscripcion;
    private EstadoInscripcion estado;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
    public Long getParticipanteId() { return participanteId; }
    public void setParticipanteId(Long participanteId) { this.participanteId = participanteId; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    public EstadoInscripcion getEstado() { return estado; }
    public void setEstado(EstadoInscripcion estado) { this.estado = estado; }
}