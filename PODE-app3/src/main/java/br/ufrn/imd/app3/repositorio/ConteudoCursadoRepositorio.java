package br.ufrn.imd.app3.repositorio;

import br.ufrn.imd.app3.modelo.ConteudoCursado;
import br.ufrn.imd.pode.repositorio.DisciplinaCursadaRepositorio;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ConteudoCursadoRepositorio extends DisciplinaCursadaRepositorio<ConteudoCursado> {

	Optional<ConteudoCursado> findByAtivoIsTrueAndLocalDateAndDisciplina_Id(LocalDate localDate, Long disciplina_id);
}
