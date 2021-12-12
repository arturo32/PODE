package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Enfase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnfaseRepository extends GenericRepository<Enfase, Long> {


	List<Enfase> findByAtivoIsTrueAndCurso_Id(Long curso_id);
}
