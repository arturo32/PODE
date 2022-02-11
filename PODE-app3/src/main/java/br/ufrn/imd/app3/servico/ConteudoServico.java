package br.ufrn.imd.app3.servico;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import org.mvel2.MVEL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaServico;

import br.ufrn.imd.app3.modelo.Conteudo;
import br.ufrn.imd.app3.modelo.dto.ConteudoDTO;
import br.ufrn.imd.app3.repositorio.ConteudoRepositorio;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ConteudoServico extends DisciplinaServico<Conteudo, ConteudoDTO> {

	private ConteudoRepositorio repositorio;

	@Autowired
	public void setRepositorio(ConteudoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public ConteudoDTO converterParaDTO(Conteudo disciplina) {
		return new ConteudoDTO(disciplina);
	}

	@Override
	public Conteudo converterParaEntidade(ConteudoDTO dto) {
		Conteudo disciplina = new Conteudo();
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

		//TODO

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
	public void validar(ConteudoDTO dto) {
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

		//TODO

		// Verifica se existe exceção
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<Conteudo, Long> repositorio() {
		return repositorio;
	}

}
