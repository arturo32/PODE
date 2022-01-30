package br.ufrn.imd.app1.repositorio;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaPeriodoRepositorio extends GenericoRepositorio<DisciplinaPeriodo, Long> {
}
