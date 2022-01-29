package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.PlanoCurso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoCursoRepositorio<T extends PlanoCurso> extends GenericoRepositorio<T, Long> {
}
