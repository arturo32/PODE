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
import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;
import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;

@Service
@Transactional
public abstract class PlanoCursoServico<T extends PlanoCurso, D extends PlanoCursoDTO,
		DiscDTO extends DisciplinaCursadaDTO> extends GenericoServico<T, D, Long> {

	public abstract DisciplinaCursadaServico<?, DiscDTO> getDisciplinaCursadaServico();

	public abstract PlanoCursoRepositorio<T> getPlanoCursoRepositorio();

	public abstract PlanoCurso criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso);

	@Override
	protected void validarModoPersistencia(TipoPersistencia tipoPersistencia, D dto) {
		ErrorPersistenciaHelper.validate(tipoPersistencia, super.obterNomeModelo(), dto);
	}

	abstract public Set<DisciplinaCursada> atualizaPendentesCursadas(Set<DisciplinaCursada> dPendentes, Set<DisciplinaCursada> cursadas);

	@Transactional(propagation = Propagation.REQUIRED)
	public T adicionarDisciplinaCursada(Long planoCursoId, List<DiscDTO> disciplinasDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		T planoCurso = this.buscarPorId(planoCursoId);
		Set<DisciplinaCursada> disciplinas = new HashSet<>();
		Collection<DisciplinaInterface> cursadas = new HashSet<>(planoCurso.getDisciplinasCursadas());
		for (DiscDTO dp : disciplinasDTOS) {
			DisciplinaCursada d = getDisciplinaCursadaServico().obterDisciplinaCursada(dp);
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

		Set<DisciplinaCursada> dCursadas = planoCurso.getDisciplinasCursadas();
		dCursadas.addAll(disciplinas);
		planoCurso.setDisciplinasCursadas(dCursadas);

		planoCurso.setDisciplinasPendentes(this.atualizaPendentesCursadas(planoCurso.getDisciplinasPendentes(), disciplinas));

		return getPlanoCursoRepositorio().save(planoCurso);
	}

	public T removerDisciplinaCursada(Long planoCursoId, List<DiscDTO> disciplinasPeriodoDTOS) {
		T planoCurso = this.buscarPorId(planoCursoId);
		Collection<DisciplinaCursada> disciplinas = new HashSet<>();
		for (DiscDTO dpDTO : disciplinasPeriodoDTOS) {
			DisciplinaCursada d = getDisciplinaCursadaServico().buscarPorId(dpDTO.getId());
			if (planoCurso.getDisciplinasCursadas().contains(d)) {
				disciplinas.add(d);
			}
		}

		Set<DisciplinaCursada> dCursadas = planoCurso.getDisciplinasCursadas();
		dCursadas.removeAll(disciplinas);
		planoCurso.setDisciplinasCursadas(dCursadas);

		Set<DisciplinaCursada> dPendentes = planoCurso.getDisciplinasPendentes();
		dPendentes.addAll(disciplinas);
		planoCurso.setDisciplinasPendentes(dPendentes);

		return getPlanoCursoRepositorio().save(planoCurso);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T adicionarDisciplinaPendente(Long planoCursoId, List<DiscDTO> disciplinasDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		T planoCurso = this.buscarPorId(planoCursoId);
		Set<DisciplinaCursada> disciplinas = new HashSet<>();
		Collection<DisciplinaInterface> cursadas = new HashSet<>(planoCurso.getDisciplinasCursadas());
		for (DiscDTO dDTO : disciplinasDTOS) {
			DisciplinaCursada d = getDisciplinaCursadaServico().obterDisciplinaCursada(dDTO);
			if (d.checarPrerequisitosDisciplinas(cursadas)) {
				cursadas.add(d);
				disciplinas.add(d);
			} else {
				exceptionHelper.add("Os pre-requisitos necessários para a disciplina '" +
						d.getCodigo() + "' não serão atendidos. Expressão: " + d.getPrerequisitos());
			}
		}
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new PrerequisitosNaoAtendidosException(exceptionHelper.getMessage());
		}

		Set<DisciplinaCursada> dPendentes = planoCurso.getDisciplinasPendentes();
		dPendentes.addAll(disciplinas);
		planoCurso.setDisciplinasPendentes(dPendentes);

		return getPlanoCursoRepositorio().save(planoCurso);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public T removerDisciplinaPendente(Long planoCursoId, List<DiscDTO> disciplinasPeriodoDTOS) {
		T planoCurso = this.buscarPorId(planoCursoId);
		Collection<DisciplinaCursada> disciplinas = new HashSet<>();
		for (DiscDTO dpDTO : disciplinasPeriodoDTOS) {
			DisciplinaCursada dp = getDisciplinaCursadaServico().obterDisciplinaCursada(dpDTO);
			disciplinas.add(dp);
		}

		Set<DisciplinaCursada> dPendentes = planoCurso.getDisciplinasPendentes();
		dPendentes.removeAll(disciplinas);
		planoCurso.setDisciplinasPendentes(dPendentes);

		return getPlanoCursoRepositorio().save(planoCurso);
	}
}
