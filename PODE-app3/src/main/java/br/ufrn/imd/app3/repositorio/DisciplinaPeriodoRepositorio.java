package br.ufrn.imd.app3.repositorio;

import br.ufrn.imd.pode.repositorio.DisciplinaCursadaRepositorio;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.app3.modelo.DisciplinaPeriodo;

import java.util.Optional;

@Repository
public interface DisciplinaPeriodoRepositorio extends DisciplinaCursadaRepositorio<DisciplinaPeriodo> {

	public Optional<DisciplinaPeriodo> findByAtivoIsTrueAndPeriodoAndDisciplina_Id(@NotNull Integer periodo, @NotNull Long disciplina_id);
}
