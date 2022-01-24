package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.Vinculo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VinculoRepositorio extends GenericoRepositorio<Vinculo, Long> {

	Optional<Vinculo> findByAtivoIsTrueAndPlanoCurso_Id(Long planoCursoId);
}
