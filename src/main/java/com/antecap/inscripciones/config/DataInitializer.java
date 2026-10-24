package com.antecap.inscripciones.config;

import com.antecap.inscripciones.enums.EstadoInscripcion;
import com.antecap.inscripciones.enums.Modalidad;
import com.antecap.inscripciones.model.Curso;
import com.antecap.inscripciones.model.Inscripcion;
import com.antecap.inscripciones.model.Participante;
import com.antecap.inscripciones.repository.CursoRepository;
import com.antecap.inscripciones.repository.InscripcionRepository;
import com.antecap.inscripciones.repository.ParticipanteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ParticipanteRepository participanteRepository;
    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;

    public DataInitializer(ParticipanteRepository participanteRepository,
                           CursoRepository cursoRepository,
                           InscripcionRepository inscripcionRepository) {
        this.participanteRepository = participanteRepository;
        this.cursoRepository = cursoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    @Override
    public void run(String... args) {
        //Participante de prueba
        Participante p1 = new Participante();
        p1.setNombres("Víctor");
        p1.setApellidos("García");
        p1.setCorreo("victor@antecap.com");
        p1.setTelefono("5555-1234");
        participanteRepository.save(p1);

        //Curso de prueba
        Curso c1 = new Curso();
        c1.setCodigo("JAVA-25");
        c1.setNombre("Spring Boot 3 y Java 25 API REST");
        c1.setDescripcion("Curso backend de arquitectura de software");
        c1.setModalidad(Modalidad.VIRTUAL);
        c1.setCupo(25);
        c1.setFechaInicio(LocalDate.now());
        c1.setActivo(true);
        cursoRepository.save(c1);

        //Inscripción de prueba
        Inscripcion ins = new Inscripcion();
        ins.setParticipante(p1);
        ins.setCurso(c1);
        ins.setFechaInscripcion(LocalDate.now());
        ins.setEstado(EstadoInscripcion.ACTIVA);
        inscripcionRepository.save(ins);

        System.out.println("\n>>> Datos de prueba insertados <<<\n");
    }
}