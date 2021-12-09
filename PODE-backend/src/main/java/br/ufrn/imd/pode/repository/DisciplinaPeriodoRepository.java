package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaPeriodoRepository extends GenericRepository<DisciplinaPeriodo, Long> {

	Optional<DisciplinaPeriodo> findByAtivoIsTrueAndPeriodoAndDisciplina_Id(@NotNull Integer periodo, @NotNull Long disciplina_id);
}
