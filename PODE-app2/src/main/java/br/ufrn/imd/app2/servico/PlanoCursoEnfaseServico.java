package br.ufrn.imd.app2.servico;

import br.ufrn.imd.app2.modelo.dto.DisciplinaPeriodoDTO;
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

import br.ufrn.imd.app2.modelo.*;
import br.ufrn.imd.app2.modelo.dto.PlanoCursoEnfaseDTO;
import br.ufrn.imd.app2.repositorio.PlanoCursoEnfaseRepositorio;

@Service
@Transactional
public class PlanoCursoEnfaseServico extends PlanoCursoServico<PlanoCursoEnfase, PlanoCursoEnfaseDTO, DisciplinaPeriodoDTO> {

	private DisciplinaBTIServico disciplinaBTIServico;
	private DisciplinaPeriodoServico disciplinaPeriodoServico;
	private EnfaseServico enfaseServico;
	private VinculoBTIServico vinculoBTIServico;

	private PlanoCursoEnfaseRepositorio repositorio;

	@Autowired
	public void setRepositorio(PlanoCursoEnfaseRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Autowired
	public void setDisciplinaPeriodoServico(DisciplinaPeriodoServico disciplinaPeriodoServico) {
		this.disciplinaPeriodoServico = disciplinaPeriodoServico;
	}

	@Autowired
	public void setEnfaseServico(EnfaseServico enfaseServico) {
		this.enfaseServico = enfaseServico;
	}

	@Autowired
	public void setVinculoBTIServico(VinculoBTIServico vinculoBTIServico) {
		this.vinculoBTIServico = vinculoBTIServico;
	}

	public DisciplinaServico<?, ?> getDisciplinaServico() {
		return disciplinaBTIServico;
	}

	@Override
	public DisciplinaCursadaServico<?, DisciplinaPeriodoDTO> getDisciplinaCursadaServico() {
		return disciplinaPeriodoServico;
	}

	@Override
	public PlanoCursoRepositorio<PlanoCursoEnfase> getPlanoCursoRepositorio() {
		return repositorio;
	}

	@Override
	public PlanoCursoEnfase criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso) {
		PlanoCursoEnfase planoCurso = new PlanoCursoEnfase();
		planoCurso.setDisciplinasPendentes(new HashSet<>(curso.getDisciplinasObrigatorias()));
		return repositorio.save(planoCurso);
	}

	@Override
	public PlanoCursoEnfaseDTO converterParaDTO(PlanoCursoEnfase entity) {
		return new PlanoCursoEnfaseDTO(entity);
	}

	@Override
	public PlanoCursoEnfase converterParaEntidade(PlanoCursoEnfaseDTO dto) {
		PlanoCursoEnfase planoCurso = new PlanoCursoEnfase();

		//Se for uma edição
		if (dto.getId() != null) {
			planoCurso = this.buscarPorId(dto.getId());
		}

		planoCurso.setId(dto.getId());
		if (dto.getIdDisciplinasCursadas() != null) {
			HashSet<DisciplinaPeriodo> disciplinas = new HashSet<>();
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasCursadas()) {
				if (disciplinaPeriodoDTO == null) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}
				try {
					disciplinas.add(this.disciplinaPeriodoServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}
			}
			planoCurso.setDisciplinasCursadas(new HashSet<>(disciplinas));
		}

		if (dto.getIdDisciplinasPendentes() != null){
			HashSet<DisciplinaPeriodo> disciplinas = new HashSet<>();
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodoDTO== null) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}
				try {
					disciplinas.add(this.disciplinaPeriodoServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}
			}
			planoCurso.setDisciplinasPendentes(new HashSet<>(disciplinas));
		}

		if (dto.getIdEnfase() != null) {
			try {
				Enfase enfase = this.enfaseServico.buscarPorId(dto.getIdEnfase());
				planoCurso.setGradeSequencial(enfase);
			}
			catch (EntidadeNaoEncontradaException entityNotFoundException) {
				throw new EntidadeInconsistenteException("enfase inconsistente");
			}
		}

		return planoCurso;
	}

	@Override
	protected void validar(PlanoCursoEnfaseDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplinasCursadas
		if (dto.getIdDisciplinasCursadas() != null) {
			for (Long disciplinaPeriodo : dto.getIdDisciplinasCursadas()) {
				if (disciplinaPeriodo == null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaCursada inconsistente");
				} else {
					try {
						this.disciplinaPeriodoServico.buscarPorId(disciplinaPeriodo);
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
						this.disciplinaPeriodoServico.buscarPorId(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entityNotFoundException) {
						exceptionHelper.add("disciplinaPendente(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		if (dto.getIdEnfase() != null) {
			if (dto.getIdEnfase() < 0) {
				exceptionHelper.add("enfase inconsistente");
			} else {
				try {
					this.enfaseServico.buscarPorId(dto.getIdEnfase());
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					exceptionHelper.add("enfase(id=" + dto.getIdEnfase() + ") inexistente");
				}
			}
		}
		//Verifica se existe exceção
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<PlanoCursoEnfase, Long> repositorio() {
		return repositorio;
	}

	public List<Integer> cargaHorariaPeriodos(PlanoCursoEnfase planoCurso, VinculoBTI vinculo) {
		CursoBTI curso = (CursoBTI) vinculo.getGradeCurricular();
		List<Integer> result = new ArrayList<>(Collections.nCopies(curso.getPrazoMaximo(), 0));
		for (DisciplinaCursada disciplinaCursada: planoCurso.getDisciplinasCursadas()) {
			DisciplinaPeriodo dp = (DisciplinaPeriodo) disciplinaCursada;
			result.set(dp.getPeriodo() - 1, result.get(dp.getPeriodo() - 1) + dp.getDisciplina().getCh());
		}
		for (DisciplinaCursada disciplinaCursada: planoCurso.getDisciplinasPendentes()) {
			DisciplinaPeriodo dp = (DisciplinaPeriodo) disciplinaCursada;
			result.set(dp.getPeriodo() - 1, result.get(dp.getPeriodo() - 1) + dp.getDisciplina().getCh());
		}
		return result.subList(vinculo.getPeriodoAtualPeriodo()-1, curso.getPrazoMaximo()-1);
	}

	public PlanoCursoEnfase alterarPlanoCursoEnfase(PlanoCursoEnfase planoCurso, Enfase enfase) {
		Set<DisciplinaCursada> obrigatoriasEnfase = enfase.getDisciplinasObrigatorias();
		Set<DisciplinaInterface> disciplinas = planoCurso.getDisciplinasCursadas().stream().map(DisciplinaCursada::getDisciplina).collect(Collectors.toSet());
		Set<DisciplinaCursada> pendentes = new HashSet<>();
		for (DisciplinaCursada dp: obrigatoriasEnfase) {
			DisciplinaBTI dbti = (DisciplinaBTI)dp.getDisciplina();
			if (!disciplinas.contains(dp.getDisciplina()) && !dbti.checarEquivalentesDisciplinas(disciplinas)) {
				pendentes.add(dp);
			}
		}
		planoCurso.setDisciplinasPendentes(pendentes);
		return this.repositorio.save(planoCurso);
	}

	public PlanoCursoEnfase atualizaEnfase(Long planoCursoId, Long enfaseId) {
		PlanoCursoEnfase planoCurso = this.buscarPorId(planoCursoId);
		Vinculo vinculo = this.vinculoBTIServico.buscarPorPlanoCursoId(planoCursoId);
		Enfase enfase = this.enfaseServico.buscarPorId(enfaseId);
		if(vinculo.getGradeCurricular().getId().equals(enfase.getCurso().getId())) {
			planoCurso.setGradeSequencial(enfase);
			return this.alterarPlanoCursoEnfase(planoCurso, enfase);
		} else {
			throw new NegocioException("Ênfase indicada não pertence ao curso que o vínculo está associado");
		}
	}

	public PlanoCursoEnfase removeEnfase(Long planoCursoId) {
		PlanoCursoEnfase planoCurso = this.buscarPorId(planoCursoId);
		Set<DisciplinaCursada> obrigatoriasEnfase = planoCurso.getGradeSequencial().getDisciplinasObrigatorias();
		Set<DisciplinaInterface> disciplinas = planoCurso.getDisciplinasCursadas().stream().map(DisciplinaCursada::getDisciplina).collect(Collectors.toSet());
		Set<DisciplinaCursada> remover = new HashSet<>();
		for (DisciplinaCursada dp: obrigatoriasEnfase) {
			DisciplinaBTI dbti = (DisciplinaBTI)dp.getDisciplina();
			if (!disciplinas.contains(dp.getDisciplina()) && !dbti.checarEquivalentesDisciplinas(disciplinas)) {
				remover.add(dp);
			}
		}
		Set<DisciplinaCursada> pendentes = planoCurso.getDisciplinasPendentes();
		pendentes.removeAll(remover);
		planoCurso.setDisciplinasPendentes(pendentes);
		return this.repositorio.save(planoCurso);
	}

	@Override
	public Set<DisciplinaCursada> atualizaPendentesCursadas(Set<DisciplinaCursada> dPendentes, Set<DisciplinaCursada> cursadas) {
		Set<DisciplinaInterface> disciplinas = cursadas.stream().map(DisciplinaCursada::getDisciplina).collect(Collectors.toSet());
		Set<DisciplinaCursada> pendentes = new HashSet<>();
		for (DisciplinaCursada dp: dPendentes) {
			DisciplinaBTI d = (DisciplinaBTI) dp.getDisciplina();
			if (!disciplinas.contains(d) && !d.checarEquivalentesDisciplinas(disciplinas)) {
				pendentes.add(dp);
			}
		}
		return pendentes;
	}
}
