package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.PlanoCurso;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoCursoRepository extends GenericRepository<PlanoCurso, Long> {

	@Query(value = "SELECT pc.* FROM planocurso AS pc, vinculo AS v WHERE pc.id = v.planocurso_id AND v.id = :vinculoId", nativeQuery = true)
	public PlanoCurso findPlanoCursoByVinculoId(Long vinculoId);
}
