package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Disciplina;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends GenericRepository<Disciplina, Long> {
}
