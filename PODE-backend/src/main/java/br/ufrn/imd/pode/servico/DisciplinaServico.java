package br.ufrn.imd.pode.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;
import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;

@Service
@Transactional
public abstract class DisciplinaServico<T extends Disciplina, D extends DisciplinaDTO> extends GenericoServico<T, D, Long> {

	private DisciplinaRepositorio<T> getDisciplinaRepositorio() {
		return (DisciplinaRepositorio<T>) this.repositorio();
	}

	@Override
	protected void validarModoPersistencia(TipoPersistencia tipoPersistencia, D dto) {
		ErrorPersistenciaHelper.validate(tipoPersistencia, super.obterNomeModelo(), dto);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Set<T> buscarDisciplinasPorCodigo(String codigo) {
		return getDisciplinaRepositorio().findDisciplinasByAtivoIsTrueAndCodigoIs(codigo);
	}

}
