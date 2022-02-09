package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import br.ufrn.imd.pode.servico.PlanoCursoServico;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.servico.DisciplinaCursadaServico;

import br.ufrn.imd.app1.modelo.*;
import br.ufrn.imd.app1.modelo.dto.PlanoCursoPesDTO;
import br.ufrn.imd.app1.repositorio.PlanoCursoPesRepositorio;

@Service
@Transactional
public class PlanoCursoPesServico extends PlanoCursoServico<PlanoCursoPes, PlanoCursoPesDTO, DisciplinaPeriodoDTO> {

	private DisciplinaBTIServico disciplinaBTIServico;
	private DisciplinaPeriodoServico disciplinaPeriodoServico;
	private PesServico pesServico;
	private VinculoBTIServico vinculoBTIServico;

	private PlanoCursoPesRepositorio repositorio;

	@Autowired
	public void setRepositorio(PlanoCursoPesRepositorio repositorio) {
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
	public void setPesServico(PesServico pesServico) {
		this.pesServico = pesServico;
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
	public PlanoCursoRepositorio<PlanoCursoPes> getPlanoCursoRepositorio() {
		return repositorio;
	}

	@Override
	public PlanoCursoPes criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso) {
		PlanoCursoPes planoCurso = new PlanoCursoPes();
		planoCurso.setDisciplinasPendentes(new HashSet<>(curso.getDisciplinasObrigatorias()));
		return repositorio.save(planoCurso);
	}

	@Override
	public PlanoCursoPesDTO converterParaDTO(PlanoCursoPes entity) {
		return new PlanoCursoPesDTO(entity);
	}

	@Override
	public PlanoCursoPes converterParaEntidade(PlanoCursoPesDTO dto) {
		PlanoCursoPes planoCurso = new PlanoCursoPes();

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

		if (dto.getIdPes() != null) {
			List<Pes> pes = new ArrayList<>();
			for (Long desDTO : dto.getIdPes()) {
				if (desDTO == null) {
					throw new EntidadeInconsistenteException("pes inconsistente");
				}

				try {
					pes.add(this.pesServico.buscarPorId(desDTO));
				}
				catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("pes inconsistente");
				}
			}
			planoCurso.setGradesParalelas(pes);
		}

		return planoCurso;
	}

	@Override
	protected void validar(PlanoCursoPesDTO dto) {
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

		//Verifica pesInteresse
		if (dto.getIdPes() != null) {
			for (Long pes : dto.getIdPes()) {
				if (pes == null || pes < 0) {
					exceptionHelper.add("disciplinaPendente inconsistente");
				} else {
					try {
						this.pesServico.buscarPorId(pes);
					} catch (EntidadeNaoEncontradaException entityNotFoundException) {
						exceptionHelper.add("pes(id=" + pes + ") inexistente");
					}
				}
			}
		}

		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<PlanoCursoPes, Long> repositorio() {
		return repositorio;
	}

	public List<Integer> cargaHorariaPeriodos(PlanoCursoPes planoCurso, VinculoBTI vinculo) {
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

	public PlanoCursoPes adicionaInteressePes(Long planoCursoId, List<Long> pesIds) {
		PlanoCursoPes planoCurso = this.buscarPorId(planoCursoId);
		VinculoBTI vinculo = vinculoBTIServico.buscarPorPlanoCursoId(planoCurso.getId());
		List<Integer> chs = this.cargaHorariaPeriodos(planoCurso, vinculo);
		List<Pes> pesList = pesServico.buscarPorIds(pesIds);
		Set<DisciplinaCursada> disciplinas = planoCurso.getDisciplinasPendentes();
		for (Pes pes : pesList) {
			for (DisciplinaCursada d: pes.getDisciplinasObrigatorias()) {
				int minIdx = vinculo.getPeriodoAtualPeriodo();
				for (int i = vinculo.getPeriodoAtualPeriodo()+1; i < chs.size(); ++i) {
					if (chs.get(i) < chs.get(minIdx)) {
						minIdx = i;
					}
				}
				if (!disciplinas.contains(d)) {
					disciplinas.add(d);
					planoCurso.getDisciplinasPendentes().add(new DisciplinaPeriodo((DisciplinaBTI) d.getDisciplina(), minIdx));
					chs.set(minIdx, chs.get(minIdx)+d.getCh());
				}
			}
		}
		planoCurso.getGradesParalelas().addAll(pesList);
		return repositorio.save(planoCurso);
	}

	public PlanoCursoPes removeInteressePes(Long planoCursoId, List<Long> pesIds) {
		PlanoCursoPes planoCurso = this.buscarPorId(planoCursoId);
		List<Pes> pesList = pesServico.buscarPorIds(pesIds);
		List<DisciplinaCursada> to_remove = new ArrayList<>();
		for (Pes pes : pesList) {
			for (DisciplinaCursada d: pes.getDisciplinasObrigatorias()) {
				for (DisciplinaCursada dp: planoCurso.getDisciplinasPendentes()) {
					if (dp.getDisciplina() == d.getDisciplina()) {
						to_remove.add(dp);
					}
				}
			}
			to_remove.forEach(planoCurso.getDisciplinasPendentes()::remove);
		}
		pesList.forEach(planoCurso.getGradesParalelas()::remove);
		return repositorio.save(planoCurso);
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
