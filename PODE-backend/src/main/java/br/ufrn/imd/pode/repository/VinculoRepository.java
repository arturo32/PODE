package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.model.Vinculo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VinculoRepository extends GenericRepository<Vinculo, Long> {

	Optional<Vinculo> findByAtivoIsTrueAndPlanoCurso_Id(Long planoCursoId);
}
