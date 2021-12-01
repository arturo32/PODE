package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Disciplina;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Repository
public interface DisciplinaRepository extends GenericRepository<Disciplina, Long> {

	Set<Disciplina> findDisciplinasByAtivoIsTrueAndCodigoIs(@NotNull @NotBlank String codigo);

	Set<Disciplina> findDisciplinasByAtivoIsTrueAndCodigoIn(Collection<@NotNull @NotBlank String> codigos);
}
