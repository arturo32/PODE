package br.ufrn.imd.app1.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaCursadaServico;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;
import br.ufrn.imd.app1.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.app1.repositorio.DisciplinaPeriodoRepositorio;

@Service
@Transactional
public class DisciplinaPeriodoServico extends DisciplinaCursadaServico<DisciplinaPeriodo, DisciplinaPeriodoDTO> {

	private DisciplinaBTIServico disciplinaBTIServico;
	private DisciplinaPeriodoRepositorio repositorio;

	@Autowired
	public void setRepositorio(DisciplinaPeriodoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	public DisciplinaPeriodoDTO converterParaDTO(DisciplinaPeriodo entity) {
		return new DisciplinaPeriodoDTO(entity);
	}

	@Override
	public DisciplinaPeriodo converterParaEntidade(DisciplinaPeriodoDTO dto) {
		DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo();
		//Se for uma edição
		if (dto.getId() != null) {
			disciplinaPeriodo = this.buscarPorId(dto.getId());
		}
		disciplinaPeriodo.setId(dto.getId());
		if (dto.getDisciplinaId() == null) {
			throw new EntidadeInconsistenteException("disciplina inconsistente");
		}
		try {
			disciplinaPeriodo
					.setDisciplina(this.disciplinaBTIServico.buscarPorId(dto.getDisciplinaId()));
		} catch (EntidadeNaoEncontradaException entityNotFoundException){
			throw new EntidadeInconsistenteException("disciplina inconsistente");
		}
		if (dto.getPeriodo() != null) {
			disciplinaPeriodo.setPeriodo(dto.getPeriodo());
		}
		return disciplinaPeriodo;
	}

	@Override
	protected void validar(DisciplinaPeriodoDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		//Verifica disciplina
		if (dto.getDisciplinaId() == null || dto.getDisciplinaId() < 0) {
			exceptionHelper.add("disciplina inconsistente");
		} else {
			try {
				this.disciplinaBTIServico.buscarPorId(dto.getDisciplinaId());
			} catch (EntidadeNaoEncontradaException entityNotFoundException) {
				exceptionHelper.add("disciplina inexistente");
			}
		}
		//Verifica período
		if (dto.getPeriodo() == null || dto.getPeriodo() <= 0) {
			exceptionHelper.add("periodo inválido");
		}
		//Verifica se existe exceção
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<DisciplinaPeriodo, Long> repositorio() {
		return repositorio;
	}
}
