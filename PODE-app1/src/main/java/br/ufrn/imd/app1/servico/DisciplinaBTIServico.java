package br.ufrn.imd.app1.servico;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import org.mvel2.MVEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaServico;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;
import br.ufrn.imd.app1.repositorio.DisciplinaBTIRepositorio;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class DisciplinaBTIServico extends DisciplinaServico<DisciplinaBTI, DisciplinaBTIDTO> {

	private DisciplinaBTIRepositorio repositorio;

	@Autowired
	public void setRepositorio(DisciplinaBTIRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public DisciplinaBTIDTO converterParaDTO(DisciplinaBTI disciplina) {
		return new DisciplinaBTIDTO(disciplina);
	}

	@Override
	public DisciplinaBTI converterParaEntidade(DisciplinaBTIDTO dto) {
		DisciplinaBTI disciplina = new DisciplinaBTI();
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

		if (dto.getEquivalentes() != null) {
			disciplina.setEquivalentes(dto.getEquivalentes());
		}

		return disciplina;
	}

	private boolean checarExpressao(String expressao) {
		try {
			expressao = expressao.replace(" E ", " && ");
			expressao = expressao.replace(" OU ", " || ");
			Matcher matcher = Pattern.compile("([A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9]{3})").matcher(expressao);
			while (matcher.find()) {
				for (int i = 0; i < matcher.groupCount(); i++) {
					expressao = expressao.replace(matcher.group(i), String.valueOf(true));
				}
			}
			return (boolean) MVEL.eval(expressao);
		} catch (org.mvel2.CompileException e) {
			return false;
		}
	}

	@Override
	public void validar(DisciplinaBTIDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		// Verifica código
		if (StringUtils.isEmpty(dto.getCodigo())) {
			exceptionHelper.add("codigo inválido");
		} else {
			Matcher matcher = Pattern.compile("([A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9]{3})").matcher(dto.getCodigo());
			if (!matcher.find()) {
				exceptionHelper.add("formato de código inválido (exemplo: ABC1234)");
			}
		}

		// Verifica nome
		if (StringUtils.isEmpty(dto.getNome())) {
			exceptionHelper.add("nome inválido");
		}
		// Verifica carga horária
		if (dto.getCh() == null || dto.getCh() <= 0) {
			exceptionHelper.add("ch inválido");
		}

		if (!dto.getPrerequisitos().equals("") && !checarExpressao(dto.getPrerequisitos())) {
			exceptionHelper.add("expressão de prequisitos inválida");
		}

		if (!dto.getEquivalentes().equals("") && !checarExpressao(dto.getEquivalentes())) {
			exceptionHelper.add("expressão de equivalencia inválida");
		}

		// Verifica se existe exceção
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<DisciplinaBTI, Long> repositorio() {
		return repositorio;
	}

	public Set<Long> obterDisciplinasObrigatoriasPendentesPes(long vinculoId, long pesId) {
		return this.repositorio.findDisciplinasObrigatoriasPendentesByVinculoAndPes(vinculoId, pesId);
	}

	public Set<Long> obterDisciplinasOptativasPendentesPes(long vinculoId, long pesId) {
		return this.repositorio.findDisciplinasOptativasPendentesByVinculoAndPes(vinculoId, pesId);
	}

}
