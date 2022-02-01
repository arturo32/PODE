package br.ufrn.imd.pode.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufrn.imd.pode.exception.PrerequisitosNaoAtendidosException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.*;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;

@Service
@Transactional
public abstract class PlanoCursoServico<T extends PlanoCurso, E extends PlanoCursoDTO> extends GenericoServico<T, E, Long> {

	public abstract DisciplinaServico<?, ?> getDisciplinaServico();

	public abstract PlanoCursoRepositorio<T> getPlanoCursoRepositorio();

	public abstract PlanoCurso criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso);

	@Transactional(propagation = Propagation.REQUIRED)
	public T adicionarDisciplinaCursada(Long planoCursoId, List<DisciplinaDTO> disciplinasDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinas = new HashSet<>();
		Collection<DisciplinaInterface> cursadas = new HashSet<>(planoCurso.getDisciplinasCursadas());
		for (DisciplinaDTO dp : disciplinasDTOS) {
			Disciplina d = getDisciplinaServico().buscarPorId(dp.getId());
			if (d.checarPrerequisitosDisciplinas(cursadas)) {
				cursadas.add(d);
				disciplinas.add(d);
			} else {
				exceptionHelper.add("Disciplina de código '" + d.getCodigo() + "' não teve os prerequisitos atendidos");
			}
		}
		if (!StringUtils.isEmpty(exceptionHelper.getMessage())) {
			throw new PrerequisitosNaoAtendidosException(exceptionHelper.getMessage());
		}
		planoCurso.getDisciplinasCursadas().addAll(disciplinas);
		planoCurso.getDisciplinasPendentes().removeAll(disciplinas);
		return getPlanoCursoRepositorio().save((T) planoCurso);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T removerDisciplinaCursada(Long planoCursoId, List<DisciplinaDTO> disciplinasPeriodoDTOS) {
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinas = new HashSet<>();
		for (DisciplinaDTO dpDTO : disciplinasPeriodoDTOS) {
			Disciplina d = getDisciplinaServico().buscarPorId(dpDTO.getId());
			if (planoCurso.getDisciplinasCursadas().contains(d)) {
				disciplinas.add(d);
			}
		}
		planoCurso.getDisciplinasCursadas().removeAll(disciplinas);
		planoCurso.getDisciplinasPendentes().addAll(disciplinas);
		return getPlanoCursoRepositorio().save((T) planoCurso);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T adicionarDisciplinaPendente(Long planoCursoId, List<DisciplinaDTO> disciplinasDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Set<DisciplinaInterface> pendentes = new HashSet<>();
		Collection<DisciplinaInterface> cursadas = new HashSet<>(planoCurso.getDisciplinasCursadas());
		for (DisciplinaDTO dDTO : disciplinasDTOS) {
			Disciplina d = getDisciplinaServico().buscarPorId(dDTO.getId());
			if (d.checarPrerequisitosDisciplinas(cursadas)) {
				cursadas.add(d);
				pendentes.add(d);
			} else {
				exceptionHelper.add("Os pre-requisitos necessários para a disciplina '" +
						d.getCodigo() + "' não serão atendidos. Expressão: " + d.getPrerequisitos());
			}
		}
		if (exceptionHelper.getMessage().isEmpty()) {
			throw new PrerequisitosNaoAtendidosException(exceptionHelper.getMessage());
		}
		planoCurso.getDisciplinasPendentes().addAll(pendentes);
		return getPlanoCursoRepositorio().save((T) planoCurso);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T removerDisciplinaPendente(Long planoCursoId, List<DisciplinaDTO> disciplinasPeriodoDTOS) {
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinas = new HashSet<>();
		for (DisciplinaDTO dpDTO : disciplinasPeriodoDTOS) {
			Disciplina dp = getDisciplinaServico().buscarPorId(dpDTO.getId());
			disciplinas.add(dp);
		}
		planoCurso.getDisciplinasPendentes().removeAll(disciplinas);
		return getPlanoCursoRepositorio().save((T) planoCurso);
	}
}
