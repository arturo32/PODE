package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public abstract class DisciplinaServico<T extends Disciplina, E extends DisciplinaDTO> extends GenericoServico<T, E, Long> {

	public abstract DisciplinaRepositorio<T> getDisciplinaRepositorio();

	public Set<T> buscarDisciplinasPorCodigo(String codigo) {
		return getDisciplinaRepositorio().findDisciplinasByAtivoIsTrueAndCodigoIs(codigo);
	}

}
