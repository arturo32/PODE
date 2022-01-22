package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.Curso;

import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepositorio extends GenericoRepositorio<Curso, Long> {
}
