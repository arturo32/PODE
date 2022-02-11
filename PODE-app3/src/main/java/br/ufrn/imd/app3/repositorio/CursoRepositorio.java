package br.ufrn.imd.app3.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.GradeCurricularRepositorio;

import br.ufrn.imd.app3.modelo.Curso;

@Repository
public interface CursoRepositorio extends GradeCurricularRepositorio<Curso> {
}
