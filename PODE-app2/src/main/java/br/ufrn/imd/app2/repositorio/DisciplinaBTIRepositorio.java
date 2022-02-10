package br.ufrn.imd.app2.repositorio;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;

import br.ufrn.imd.app2.modelo.DisciplinaBTI;

import java.util.List;

@Repository
public interface DisciplinaBTIRepositorio extends DisciplinaRepositorio<DisciplinaBTI> {

	List<DisciplinaBTI> findDisciplinaBTISByAtivoIsTrueAndPrerequisitosEquals(String prerequisitos, Pageable pageable);
}
