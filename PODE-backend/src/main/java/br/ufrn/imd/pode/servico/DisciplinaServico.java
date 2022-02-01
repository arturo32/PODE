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

@Service
@Transactional
public abstract class DisciplinaServico<T extends Disciplina, E extends DisciplinaDTO> extends GenericoServico<T, E, Long> {

	private DisciplinaRepositorio<T> getDisciplinaRepositorio() {
		return (DisciplinaRepositorio<T>) this.repositorio();
	}

	@Override
	public void validar(E disciplina) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		// Verifica código
		if (StringUtils.isEmpty(disciplina.getCodigo())) {
			exceptionHelper.add("codigo inválido");
		} else {
			Matcher matcher = Pattern.compile("([A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9]{3})").matcher(disciplina.getCodigo());
			if (!matcher.find()) {
				exceptionHelper.add("formato de código inválido (exemplo: ABC1234)");
			}
		}
		// Verifica nome
		if (StringUtils.isEmpty(disciplina.getNome())) {
			exceptionHelper.add("nome inválido");
		}
		// Verifica carga horária
		if (disciplina.getCh() == null || disciplina.getCh() <= 0) {
			exceptionHelper.add("ch inválido");
		}
		// TODO verificar expressões de prequisitos, equivalencias e corequisitos
		// Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
		} else {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Set<T> buscarDisciplinasPorCodigo(String codigo) {
		return getDisciplinaRepositorio().findDisciplinasByAtivoIsTrueAndCodigoIs(codigo);
	}

}
