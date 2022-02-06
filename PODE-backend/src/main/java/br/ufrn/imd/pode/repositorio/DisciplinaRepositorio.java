package br.ufrn.imd.pode.repositorio;

import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import br.ufrn.imd.pode.modelo.Disciplina;

@Repository
public interface DisciplinaRepositorio<T extends Disciplina> extends GenericoRepositorio<T, Long> {

	Set<T> findDisciplinasByAtivoIsTrueAndCodigoIs(@NotNull @NotBlank String codigo);

}
