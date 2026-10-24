package com.antecap.inscripciones.service;

import com.antecap.inscripciones.model.Curso;
import com.antecap.inscripciones.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    public Curso buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
    }

    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}