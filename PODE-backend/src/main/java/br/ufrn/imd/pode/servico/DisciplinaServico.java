package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class DisciplinaServico extends GenericoServico<Disciplina, DisciplinaDTO, Long> {

	private DisciplinaRepositorio repository;

	@Override
	public DisciplinaDTO converterParaDTO(Disciplina disciplina) {
		return new DisciplinaDTO(disciplina);
	}

	@Override
	public Disciplina converterParaEntidade(DisciplinaDTO dto) {
		Disciplina disciplina = new Disciplina();

		// Se for uma edição
		if (dto.getId() != null) {
			disciplina = this.buscarPorId(dto.getId());
		}

		disciplina.setId(dto.getId());
		if (dto.getCodigo() != null) {
			disciplina.setCodigo(dto.getCodigo());
		}
		if (dto.getNome() != null) {
			disciplina.setNome(dto.getNome());
		}
		if (dto.getCh() != null) {
			disciplina.setCh(dto.getCh());
		}
		if (dto.getPrerequisitos() != null) {
			disciplina.setPrerequisitos(dto.getPrerequisitos());
		}

		return disciplina;
	}

	@Override
	protected GenericoRepositorio<Disciplina, Long> repositorio() {
		return this.repository;
	}

	public DisciplinaRepositorio getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(DisciplinaRepositorio disciplinaRepository) {
		this.repository = disciplinaRepository;
	}

	public Set<Disciplina> buscarDisciplinasPorCodigo(String codigo) {
		return this.repository.findDisciplinasByAtivoIsTrueAndCodigoIs(codigo);
	}

	@Override
	public void validar(DisciplinaDTO disciplina) {
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

}
