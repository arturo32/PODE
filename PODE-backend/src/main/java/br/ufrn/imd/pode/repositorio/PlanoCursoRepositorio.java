package br.ufrn.imd.pode.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.modelo.PlanoCurso;

@Repository
public interface PlanoCursoRepositorio<T extends PlanoCurso> extends GenericoRepositorio<T, Long> {
}
