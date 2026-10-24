package com.antecap.inscripciones.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID> {
    T guardar(T entidad);
    Optional<T> buscarPorId(ID id);
    List<T> listarTodos();
    void eliminar(ID id);
}