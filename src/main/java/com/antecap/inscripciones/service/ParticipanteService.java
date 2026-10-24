package com.antecap.inscripciones.service;

import com.antecap.inscripciones.model.Participante;
import com.antecap.inscripciones.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository repository;

    public ParticipanteService(ParticipanteRepository repository) {
        this.repository = repository;
    }

    public List<Participante> listarTodos() {
        return repository.findAll();
    }

    public Participante buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante no encontrado con ID: " + id));
    }

    public Participante guardar(Participante participante) {
        return repository.save(participante);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}