package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.PrerequisitosNaoAtendidosException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.PlanoCurso;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public abstract class PlanoCursoServico extends GenericoServico<PlanoCurso, PlanoCursoDTO, Long> {

	private PlanoCursoRepositorio repositorio;
	private DisciplinaServico disciplinaServico;
	private VinculoServico vinculoService;

	@Override
	public PlanoCursoDTO converterParaDTO(PlanoCurso planoCurso) {
		return new PlanoCursoDTO(planoCurso);
	}

	@Override
	protected GenericoRepositorio<PlanoCurso, Long> repositorio() {
		return this.repositorio;
	}

	public PlanoCursoRepositorio getRepositorio() {
		return this.repositorio;
	}

	@Autowired
	public void setRepositorio(PlanoCursoRepositorio planoCursoRepository) {
		this.repositorio = planoCursoRepository;
	}

	public DisciplinaServico getDisciplinaServico() {
		return disciplinaServico;
	}

	@Autowired
	public void setDisciplinaServico(DisciplinaServico disciplinaServico) {
		this.disciplinaServico = disciplinaServico;
	}

	public VinculoServico getVinculoService() {
		return vinculoService;
	}

	@Autowired
	public void setVinculoService(VinculoServico vinculoService) {
		this.vinculoService = vinculoService;
	}

	@Override
	public void validar(PlanoCursoDTO planoCurso) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplinasCursadas
		if (planoCurso.getIdDisciplinasCursadas() != null) {
			for (Long disciplinaPeriodo : planoCurso.getIdDisciplinasCursadas()) {
				if (disciplinaPeriodo == null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaCursada inconsistente");
				} else {
					try {
						this.disciplinaServico.buscarPorId(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
						exceptionHelper.add("disciplinaCursada(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica disciplinasPendentes
		if (planoCurso.getIdDisciplinasPendentes() != null) {
			for (Long disciplinaPeriodo : planoCurso.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodo == null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaPendente inconsistente");
				} else {
					try {
						this.disciplinaServico.buscarPorId(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
						exceptionHelper.add("disciplinaPendente(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}
		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
		} else {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	public abstract PlanoCurso criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso);

	public PlanoCurso adicionarDisciplinaCursada(Long planoCursoId, List<DisciplinaDTO> disciplinasDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinas = new HashSet<>();
		Collection<DisciplinaInterface> cursadas = new HashSet<>(planoCurso.getDisciplinasCursadas());
		for (DisciplinaDTO dp : disciplinasDTOS) {
			Disciplina d = disciplinaServico.buscarPorId(dp.getId());
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
		return repositorio.save(planoCurso);
	}

	public PlanoCurso removerDisciplinaCursada(Long planoCursoId, List<DisciplinaDTO> disciplinasPeriodoDTOS) {
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinas = new HashSet<>();
		for (DisciplinaDTO dpDTO : disciplinasPeriodoDTOS) {
			Disciplina d = disciplinaServico.buscarPorId(dpDTO.getId());
			if (planoCurso.getDisciplinasCursadas().contains(d)) {
				disciplinas.add(d);
			}
		}
		planoCurso.getDisciplinasCursadas().removeAll(disciplinas);
		planoCurso.getDisciplinasPendentes().addAll(disciplinas);
		return repositorio.save(planoCurso);
	}

	public PlanoCurso adicionarDisciplinaPendente(Long planoCursoId, List<DisciplinaDTO> disciplinasDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Set<DisciplinaInterface> naoPendentes = new HashSet<>();
		Collection<DisciplinaInterface> cursadas = new HashSet<>(planoCurso.getDisciplinasCursadas());
		for (DisciplinaDTO dDTO : disciplinasDTOS) {
			Disciplina d = disciplinaServico.buscarPorId(dDTO.getId());
			if (d.checarPrerequisitosDisciplinas(cursadas)) {
				cursadas.add(d);
				naoPendentes.add(d);
			} else {
				exceptionHelper.add("Os pre-requisitos necessários para a disciplina '" +
						d.getCodigo() + "' não serão atendidos. Expressão: " + d.getPrerequisitos());
			}
		}
		if (exceptionHelper.getMessage().isEmpty()) {
			throw new PrerequisitosNaoAtendidosException(exceptionHelper.getMessage());
		}
		planoCurso.setDisciplinasPendentes(naoPendentes);
		return repositorio.save(planoCurso);
	}

	public PlanoCurso removerDisciplinaPendente(Long planoCursoId, List<DisciplinaDTO> disciplinasPeriodoDTOS) {
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinas = new HashSet<>();
		for (DisciplinaDTO dpDTO : disciplinasPeriodoDTOS) {
			Disciplina dp = disciplinaServico.buscarPorId(dpDTO.getId());
			disciplinas.add(dp);
		}
		planoCurso.getDisciplinasPendentes().removeAll(disciplinas);
		return repositorio.save(planoCurso);
	}
}
