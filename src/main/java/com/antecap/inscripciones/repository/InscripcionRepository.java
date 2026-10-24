package com.antecap.inscripciones.repository;

import com.antecap.inscripciones.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}