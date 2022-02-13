package br.ufrn.imd.app3.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;
import br.ufrn.imd.pode.servico.PlanoCursoServico;
import br.ufrn.imd.pode.servico.DisciplinaCursadaServico;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.*;

import br.ufrn.imd.app3.modelo.*;
import br.ufrn.imd.app3.modelo.dto.PlanoCursoTemaDTO;
import br.ufrn.imd.app3.repositorio.PlanoCursoTemaRepositorio;
import br.ufrn.imd.app3.modelo.dto.ConteudoCursadoDTO;

@Service
@Transactional
public class PlanoCursoTemaServico extends PlanoCursoServico<PlanoCursoTema, PlanoCursoTemaDTO, ConteudoCursadoDTO> {

	private ConteudoCursadoServico conteudoCursadoServico;
	private TemaServico temaServico;
	private PlanoCursoTemaRepositorio repositorio;

	@Autowired
	public void setRepositorio(PlanoCursoTemaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setConteudoCursadoServico(ConteudoCursadoServico conteudoCursadoServico) {
		this.conteudoCursadoServico = conteudoCursadoServico;
	}

	@Autowired
	public void setTemaServico(TemaServico temaServico) {
		this.temaServico = temaServico;
	}

	@Override
	public DisciplinaCursadaServico<?, ConteudoCursadoDTO> getDisciplinaCursadaServico() {
		return conteudoCursadoServico;
	}

	@Override
	public PlanoCursoRepositorio<PlanoCursoTema> getPlanoCursoRepositorio() {
		return repositorio;
	}

	@Override
	public PlanoCursoTema criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso) {
		PlanoCursoTema planoCurso = new PlanoCursoTema();
		planoCurso.setDisciplinasPendentes(new HashSet<>(curso.getDisciplinasObrigatorias()));
		return repositorio.save(planoCurso);
	}

	@Override
	public Set<DisciplinaCursada> atualizaPendentesCursadas(Set<DisciplinaCursada> dPendentes, Set<DisciplinaCursada> cursadas) {
		dPendentes.removeAll(cursadas);
		return dPendentes;
	}

	@Override
	public PlanoCursoTemaDTO converterParaDTO(PlanoCursoTema entity) {
		return new PlanoCursoTemaDTO(entity);
	}

	@Override
	public PlanoCursoTema converterParaEntidade(PlanoCursoTemaDTO dto) {
		PlanoCursoTema planoCurso = new PlanoCursoTema();

		//Se for uma edição
		if (dto.getId() != null) {
			planoCurso = this.buscarPorId(dto.getId());
		}

		planoCurso.setId(dto.getId());
		if (dto.getIdDisciplinasCursadas() != null) {
			HashSet<ConteudoCursado> disciplinas = new HashSet<>();
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasCursadas()) {
				if (disciplinaPeriodoDTO == null) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}
				try {
					disciplinas.add(this.conteudoCursadoServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}
			}
			planoCurso.setDisciplinasCursadas(new HashSet<>(disciplinas));
		}

		if (dto.getIdDisciplinasPendentes() != null){
			HashSet<ConteudoCursado> disciplinas = new HashSet<>();
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodoDTO== null) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}
				try {
					disciplinas.add(this.conteudoCursadoServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}
			}
			planoCurso.setDisciplinasPendentes(new HashSet<>(disciplinas));
		}

		if (dto.getTemas() != null) {
			HashSet<Tema> temas = new HashSet<>();
			for (Long temaId: dto.getTemas()) {
				if (temaId == null) {
					throw new EntidadeInconsistenteException("tema inconsistente");
				}
				try {
					temas.add(this.temaServico.buscarPorId(temaId));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("tema inconsistente");
				}
			}
			planoCurso.setTemas(temas);
		}

		return planoCurso;
	}

	@Override
	protected void validar(PlanoCursoTemaDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplinasCursadas
		if (dto.getIdDisciplinasCursadas() != null) {
			for (Long disciplinaPeriodo : dto.getIdDisciplinasCursadas()) {
				if (disciplinaPeriodo == null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaCursada inconsistente");
				} else {
					try {
						this.conteudoCursadoServico.buscarPorId(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entityNotFoundException) {
						exceptionHelper.add("disciplinaCursada(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica disciplinasPendentes
		if (dto.getIdDisciplinasPendentes() != null) {
			for (Long disciplinaPeriodo : dto.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodo== null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaPendente inconsistente");
				} else {
					try {
						this.conteudoCursadoServico.buscarPorId(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entityNotFoundException) {
						exceptionHelper.add("disciplinaPendente(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		if (dto.getTemas() != null) {
			for (Long temaId : dto.getTemas()) {
				if (temaId== null || temaId < 0) {
					exceptionHelper.add("tema inconsistente");
				} else {
					try {
						this.temaServico.buscarPorId(temaId);
					} catch (EntidadeNaoEncontradaException entityNotFoundException) {
						exceptionHelper.add("tema(id=" + temaId + ") inexistente");
					}
				}
			}
		}

		//Verifica se existe exceção
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<PlanoCursoTema, Long> repositorio() {
		return repositorio;
	}
}
