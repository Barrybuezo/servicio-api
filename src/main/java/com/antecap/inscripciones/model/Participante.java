package com.antecap.inscripciones.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "participantes")
public class Participante extends Persona {
}