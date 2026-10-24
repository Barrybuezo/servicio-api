package com.antecap.inscripciones.repository;

import com.antecap.inscripciones.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Optional<Participante> findByCorreo(String correo);
}