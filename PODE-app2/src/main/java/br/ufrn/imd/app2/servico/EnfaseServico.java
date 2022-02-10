package br.ufrn.imd.app2.servico;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.servico.GradeCurricularServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import br.ufrn.imd.app2.modelo.Enfase;
import br.ufrn.imd.app2.modelo.dto.EnfaseDTO;
import br.ufrn.imd.app2.repositorio.EnfaseRepositorio;
import org.springframework.util.StringUtils;

import java.util.HashSet;

@Service
@Transactional
public class EnfaseServico extends GradeCurricularServico<Enfase, EnfaseDTO> {

	private CursoBTIServico cursoBTIServico;
	private DisciplinaPeriodoServico disciplinaPeriodoServico;
	private DisciplinaBTIServico disciplinaBTIServico;
	private EnfaseRepositorio repositorio;

	@Autowired
	public void setRepositorio(EnfaseRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setCursoBTIServico(CursoBTIServico cursoBTIServico) {
		this.cursoBTIServico = cursoBTIServico;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Autowired
	public void setDisciplinaPeriodoServico(DisciplinaPeriodoServico disciplinaPeriodoServico) {
		this.disciplinaPeriodoServico = disciplinaPeriodoServico;
	}

	@Override
	protected GenericoRepositorio<Enfase, Long> repositorio() {
		return this.repositorio;
	}

	@Override
	public EnfaseDTO converterParaDTO(Enfase entity) {
		return new EnfaseDTO(entity);
	}

	@Override
	public Enfase converterParaEntidade(EnfaseDTO dto) {
		Enfase enfase = new Enfase();

		//Se for uma edição
		if (dto.getId() != null) {
			enfase = this.buscarPorId(dto.getId());
		}

		enfase.setId(dto.getId());
		if(dto.getNome() != null){
			enfase.setNome(dto.getNome());
		}

		if (dto.getCursoId() == null) {
			throw new EntidadeInconsistenteException("curso inconsistente");
		}
		try {
			enfase.setCurso(this.cursoBTIServico.buscarPorId(dto.getCursoId()));
		} catch (EntidadeNaoEncontradaException entityNotFoundException){
			throw new EntidadeInconsistenteException("curso inconsistente");
		}

		if (dto.getDisciplinasObrigatorias() != null) {
			enfase.setDisciplinasObrigatorias(new HashSet<>());
			for (Long disciplinaPeriodoDTO : dto.getDisciplinasObrigatorias()) {
				if (disciplinaPeriodoDTO== null) {
					throw new EntidadeInconsistenteException("disciplinaPeriodo inconsistente");
				}
				try {
					enfase.getDisciplinasObrigatorias()
							.add(this.disciplinaPeriodoServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entityNotFoundException){
					throw new EntidadeInconsistenteException("disciplinaPeriodo inconsistente");
				}
			}
		}

		return enfase;
	}

	@Override
	protected void validar(EnfaseDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica nome
		if (StringUtils.isEmpty(dto.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		//Verifica curso
		if (dto.getCursoId() == null || dto.getCursoId() < 0) {
			exceptionHelper.add("curso inconsistente");
		} else {
			try {
				this.cursoBTIServico.buscarPorId(dto.getCursoId());
			} catch (EntidadeNaoEncontradaException entityNotFoundException) {
				exceptionHelper.add("curso inexistente");
			}
		}

		//Verifica disciplinas obrigatórias
		if (dto.getDisciplinasObrigatorias() != null) {
			for (Long disciplinaPeriodo : dto.getDisciplinasObrigatorias()) {
				if (disciplinaPeriodo == null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.disciplinaPeriodoServico.buscarPorId(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entityNotFoundException) {
						exceptionHelper.add("disciplinaObrigatoria(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica se existe exceção
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

}
