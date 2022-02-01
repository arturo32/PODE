package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.view.DisciplinaPendente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Repository
public interface DisciplinaRepositorio<T extends Disciplina> extends GenericoRepositorio<T, Long> {

	Set<T> findDisciplinasByAtivoIsTrueAndCodigoIs(@NotNull @NotBlank String codigo);

	Set<T> findDisciplinasByAtivoIsTrueAndCodigoIn(Collection<@NotNull @NotBlank String> codigos);

}
