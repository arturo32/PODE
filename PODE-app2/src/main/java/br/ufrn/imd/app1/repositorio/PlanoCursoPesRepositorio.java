package br.ufrn.imd.app1.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;

import br.ufrn.imd.app1.modelo.PlanoCursoPes;

@Repository
public interface PlanoCursoPesRepositorio extends PlanoCursoRepositorio<PlanoCursoPes> {
}
