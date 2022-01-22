package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.DisciplinaPeriodo;

import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface DisciplinaPeriodoRepositorio extends GenericoRepositorio<DisciplinaPeriodo, Long> {

	public Optional<DisciplinaPeriodo> findByAtivoIsTrueAndPeriodoAndDisciplina_Id(@NotNull Integer periodo, @NotNull Long disciplina_id);
}
