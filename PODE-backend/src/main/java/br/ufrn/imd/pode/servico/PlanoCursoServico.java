package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.PrerequisitosNaoAtendidosException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Disciplina;
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
	public PlanoCurso converterParaEntidade(PlanoCursoDTO dto) {
		PlanoCurso planoCurso = new PlanoCurso();

		//Se for uma edição
		if (dto.getId() != null) {
			planoCurso = this.buscarPorId(dto.getId());
		}

		planoCurso.setId(dto.getId());
		if (dto.getIdDisciplinasCursadas() != null) {
			planoCurso.setDisciplinasCursadas(new HashSet<>());
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasCursadas()) {
				if (disciplinaPeriodoDTO == null) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}

				try {
					planoCurso.getDisciplinasCursadas()
							.add(this.disciplinaServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}
			}
		}

		if (dto.getIdDisciplinasPendentes() != null) {
			planoCurso.setDisciplinasPendentes(new HashSet<>());
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodoDTO == null) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}

				try {
					planoCurso.getDisciplinasPendentes()
							.add(this.disciplinaServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}
			}
		}

		return planoCurso;
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
	public PlanoCursoDTO validar(PlanoCursoDTO planoCurso) {
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
			return planoCurso;
		} else {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	public PlanoCurso criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso) {
		PlanoCurso planoCurso = new PlanoCurso();
		planoCurso.setDisciplinasPendentes(new HashSet<>(curso.getDisciplinas()));
		return repositorio.save(planoCurso);
	}

	public PlanoCurso adicionarDisciplinaCursada(Long planoCursoId, List<DisciplinaDTO> disciplinasDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinas = new HashSet<>();
		Set<Disciplina> cursadas = planoCurso.getDisciplinasCursadas();
		for (DisciplinaDTO dp : disciplinasDTOS) {
			Disciplina d = disciplinaServico.buscarPorId(dp.getId());
			if (disciplinaServico.checarPrerequisitos(cursadas, d)) {
				cursadas.add(d);
				disciplinas.add(disciplinaServico.buscarPorId(dp.getId()));
			} else {
				exceptionHelper.add("Disciplina de código '" + d.getCodigo() + "' não teve os prerequisitos atendidos");
			}
		}
		if (!StringUtils.isEmpty(exceptionHelper.getMessage())) {
			throw new PrerequisitosNaoAtendidosException(exceptionHelper.getMessage());
		}
		planoCurso.getDisciplinasCursadas().addAll(disciplinas);
		Set<Disciplina> pendentes = new HashSet<>(planoCurso.getDisciplinasPendentes());
		planoCurso.setDisciplinasPendentes(pendentes);
		return repositorio.save(planoCurso);
	}

	public PlanoCurso removerDisciplinaCursada(Long planoCursoId, List<DisciplinaDTO> disciplinasPeriodoDTOS) {
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinasPeriodo = new HashSet<>();
		for (DisciplinaDTO dpDTO : disciplinasPeriodoDTOS) {
			Disciplina dp = disciplinaServico.buscarPorId(dpDTO.getId());
			disciplinasPeriodo.add(dp);
		}
		disciplinasPeriodo.forEach(planoCurso.getDisciplinasCursadas()::remove);
		return repositorio.save(planoCurso);
	}

	public abstract PlanoCurso adicionarDisciplinaPendente(Long planoCursoId, List<DisciplinaDTO> disciplinasDTOS);

	public PlanoCurso removerDisciplinaPendente(Long planoCursoId, List<DisciplinaDTO> disciplinasPeriodoDTOS) {
		// TODO: validação de tempo minimo por semestre
		PlanoCurso planoCurso = this.buscarPorId(planoCursoId);
		Collection<Disciplina> disciplinasPeriodo = new HashSet<>();
		for (DisciplinaDTO dpDTO : disciplinasPeriodoDTOS) {
			Disciplina dp = disciplinaServico.buscarPorId(dpDTO.getId());
			disciplinasPeriodo.add(dp);
		}
		disciplinasPeriodo.forEach(planoCurso.getDisciplinasPendentes()::remove);
		return repositorio.save(planoCurso);
	}
}
