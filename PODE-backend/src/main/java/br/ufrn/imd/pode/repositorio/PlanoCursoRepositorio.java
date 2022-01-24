package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.PlanoCurso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoCursoRepositorio extends GenericoRepositorio<PlanoCurso, Long> {

	@Query(value = "SELECT pc.* FROM planocurso AS pc, vinculo AS v WHERE pc.id = v.planocurso_id AND v.id = :vinculoId", nativeQuery = true)
	PlanoCurso findPlanoCursoByVinculoId(Long vinculoId);
}
