package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Curso;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends GenericRepository<Curso, Long> {
}
