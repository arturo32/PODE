package br.ufrn.imd.app3.servico;

import br.ufrn.imd.app3.modelo.dto.ConteudoCursadoDTO;
import br.ufrn.imd.pode.exception.NegocioException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import br.ufrn.imd.pode.servico.PlanoCursoServico;
import br.ufrn.imd.pode.servico.DisciplinaCursadaServico;

import br.ufrn.imd.app3.modelo.*;
import br.ufrn.imd.app3.modelo.dto.PlanoCursoTemaDTO;
import br.ufrn.imd.app3.repositorio.PlanoCursoTemaRepositorio;

@Service
@Transactional
public class PlanoCursoTemaServico extends PlanoCursoServico<PlanoCursoTema, PlanoCursoTemaDTO, ConteudoCursadoDTO> {

	private ConteudoCursadoServico conteudoCursadoServico;

	private PlanoCursoTemaRepositorio repositorio;

	@Autowired
	public void setRepositorio(PlanoCursoTemaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setConteudoCursadoServico(ConteudoCursadoServico conteudoCursadoServico) {
		this.conteudoCursadoServico = conteudoCursadoServico;
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

		// TODO

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

		//TODO
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
