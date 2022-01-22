package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.PrerequisitosNaoAtendidosException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Curso;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaPeriodo;
import br.ufrn.imd.pode.modelo.Enfase;
import br.ufrn.imd.pode.modelo.Pes;
import br.ufrn.imd.pode.modelo.PlanoCurso;
import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanoCursoServico extends GenericoServico<PlanoCurso, PlanoCursoDTO, Long> {

	private PlanoCursoRepositorio repository;
	private DisciplinaPeriodoServico disciplinaPeriodoService;
	private DisciplinaServico disciplinaService;
	private PesServico pesService;
	private VinculoServico vinculoService;
	private EnfaseServico enfaseService;

	@Override
	public PlanoCursoDTO convertToDto(PlanoCurso planoCurso) {
		return new PlanoCursoDTO(planoCurso);
	}

	@Override
	public PlanoCurso convertToEntity(PlanoCursoDTO dto) {
		PlanoCurso planoCurso = new PlanoCurso();

		//Se for uma edição
		if (dto.getId() != null) {
			planoCurso = this.findById(dto.getId());
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
							.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}
			}
		}

		if (dto.getIdDisciplinasPendentes() != null){
			planoCurso.setDisciplinasPendentes(new HashSet<>());
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodoDTO== null) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}

				try {
					planoCurso.getDisciplinasPendentes()
							.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}
			}
		}

		if (dto.getIdPesInteresse() != null) {
			planoCurso.setPesInteresse(new HashSet<>());
			for (Long desDTO : dto.getIdPesInteresse()) {
				if (desDTO == null) {
					throw new EntidadeInconsistenteException("pes inconsistente");
				}

				try {
					planoCurso.getPesInteresse()
							.add(this.pesService.findById(desDTO));
				}
				catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
					throw new EntidadeInconsistenteException("pes inconsistente");
				}
			}
		}

		return planoCurso;
	}

	@Override
	protected GenericoRepositorio<PlanoCurso, Long> repository() {
		return this.repository;
	}

	public PlanoCursoRepositorio getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(PlanoCursoRepositorio planoCursoRepository) {
		this.repository = planoCursoRepository;
	}

	public DisciplinaPeriodoServico getDisciplinaPeriodoService() {
		return this.disciplinaPeriodoService;
	}

	@Autowired
	public void setDisciplinaPeriodoService(DisciplinaPeriodoServico disciplinaPeriodoService) {
		this.disciplinaPeriodoService = disciplinaPeriodoService;
	}

	public PesServico getPesService() {
		return this.pesService;
	}

	@Autowired
	public void setPesService(PesServico pesService) {
		this.pesService = pesService;
	}

	public DisciplinaServico getDisciplinaService() {
		return disciplinaService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaServico disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	public VinculoServico getVinculoService() {
		return vinculoService;
	}

	@Autowired
	public void setVinculoService(VinculoServico vinculoService) {
		this.vinculoService = vinculoService;
	}

	public EnfaseServico getEnfaseService() {
		return enfaseService;
	}

	@Autowired
	public void setEnfaseService(EnfaseServico enfaseService) {
		this.enfaseService = enfaseService;
	}

	@Override
	public PlanoCursoDTO validate(PlanoCursoDTO planoCurso) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplinasCursadas
		if (planoCurso.getIdDisciplinasCursadas() != null) {
			for (Long disciplinaPeriodo : planoCurso.getIdDisciplinasCursadas()) {
				if (disciplinaPeriodo == null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaCursada inconsistente");
				} else {
					try {
						this.disciplinaPeriodoService.findById(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
						exceptionHelper.add("disciplinaCursada(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica disciplinasPendentes
		if (planoCurso.getIdDisciplinasPendentes() != null) {
			for (Long disciplinaPeriodo : planoCurso.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodo== null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaPendente inconsistente");
				} else {
					try {
						this.disciplinaPeriodoService.findById(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
						exceptionHelper.add("disciplinaPendente(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica pesInteresse
		if (planoCurso.getIdPesInteresse() != null) {
			for (Long pes : planoCurso.getIdPesInteresse()) {
				if (pes == null || pes < 0) {
					exceptionHelper.add("disciplinaPendente inconsistente");
				} else {
					try {
						this.pesService.findById(pes);
					} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
						exceptionHelper.add("pes(id=" + pes + ") inexistente");
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

	public PlanoCurso criarPlanoDeCursoUsandoCurso(Curso curso) {
		PlanoCurso planoCurso = new PlanoCurso();
		planoCurso.setDisciplinasPendentes(new HashSet<>(curso.getDisciplinasObrigatorias()));
		return repository.save(planoCurso);
	}

	public PlanoCurso criarPlanoDeCursoUsandoEnfase(Enfase enfase) {
		PlanoCurso planoCurso = new PlanoCurso();
		planoCurso.setDisciplinasPendentes(new HashSet<>(enfase.getDisciplinasObrigatorias()));
		return repository.save(planoCurso);
	}

	public PlanoCurso findPlanoCursoByVinculoId(Long vinculoId) {
		return repository.findPlanoCursoByVinculoId(vinculoId);
	}

	public PlanoCurso adicionaDisciplinaCursada(Long planoCursoId, List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		PlanoCurso planoCurso = this.findById(planoCursoId);
		Collection<DisciplinaPeriodo> disciplinasPeriodo = new HashSet<>();
		Set<Disciplina> cursadas = planoCurso.getDisciplinasCursadas().stream().map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet());
		for (DisciplinaPeriodoDTO dp: disciplinasPeriodoDTOS) {
			Disciplina d =disciplinaService.findById(dp.getIdDisciplina());
			if (disciplinaService.checarPrerequisitos(cursadas, d)) {
				cursadas.add(d);
				disciplinasPeriodo.add(disciplinaPeriodoService.getDisciplinaPeriodoPorPeriodoDisciplinaId(dp.getPeriodo(), dp.getIdDisciplina()));
			} else {
				exceptionHelper.add("Disciplina de código '" + d.getCodigo() + "' não teve os prerequisitos atendidos");
			}
		}
		if (!StringUtils.isEmpty(exceptionHelper.getMessage())) {
			throw new PrerequisitosNaoAtendidosException(exceptionHelper.getMessage());
		}
		planoCurso.getDisciplinasCursadas().addAll(disciplinasPeriodo);
		Set<Disciplina> disciplinas = planoCurso.getDisciplinasCursadas().stream().map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet());
		Set<DisciplinaPeriodo> pendentes = new HashSet<>();
		for (DisciplinaPeriodo dp: planoCurso.getDisciplinasPendentes()) {
			if (!disciplinas.contains(dp.getDisciplina()) && !disciplinaService.checarEquivalencia(disciplinas, dp.getDisciplina())) {
				pendentes.add(dp);
			}
		}
		planoCurso.setDisciplinasPendentes(pendentes);
		return repository.save(planoCurso);
	}

	public PlanoCurso removeDisciplinaCursada(Long planoCursoId, List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		PlanoCurso planoCurso = this.findById(planoCursoId);
		Collection<DisciplinaPeriodo> disciplinasPeriodo = new HashSet<>();
		for (DisciplinaPeriodoDTO dpDTO: disciplinasPeriodoDTOS) {
			DisciplinaPeriodo dp = disciplinaPeriodoService.getDisciplinaPeriodoPorPeriodoDisciplinaId(dpDTO.getPeriodo(), dpDTO.getIdDisciplina());
			disciplinasPeriodo.add(dp);
		}
		disciplinasPeriodo.forEach(planoCurso.getDisciplinasCursadas()::remove);
		return repository.save(planoCurso);
	}

	public PlanoCurso adicionaDisciplinaPendente(Long planoCursoId, List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		// TODO: validação de tempo maximo por semestre
		PlanoCurso planoCurso = this.findById(planoCursoId);
		Collection<Disciplina> disciplinasCursadas = planoCurso.getDisciplinasCursadas().stream().map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet());

		Collection<DisciplinaPeriodo> disciplinasPeriodoValidas = new HashSet<>();
		for (DisciplinaPeriodoDTO dpDTO: disciplinasPeriodoDTOS) {
			DisciplinaPeriodo dp = disciplinaPeriodoService.getDisciplinaPeriodoPorPeriodoDisciplinaId(dpDTO.getPeriodo(), dpDTO.getIdDisciplina());
			Collection<Disciplina> disciplinasFuturamenteCursadas = planoCurso.getDisciplinasPendentes().stream().
					filter(disciplinaPeriodo -> disciplinaPeriodo.getPeriodo() < dp.getPeriodo()).
					map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet());
			disciplinasFuturamenteCursadas.addAll(disciplinasCursadas);
			if (disciplinaService.checarPrerequisitos(disciplinasFuturamenteCursadas, dp.getDisciplina())) {
				disciplinasPeriodoValidas.add(dp);
			} else {
				exceptionHelper.add("Os pre-requisitos necessários para a disciplina '" +
						dp.getDisciplina().getCodigo() + "' não serão atendidos. Expressão: " + dp.getDisciplina().getPrerequisitos());
			}
		}
		if (exceptionHelper.getMessage().isEmpty()) {
			throw new PrerequisitosNaoAtendidosException(exceptionHelper.getMessage());
		}
		planoCurso.getDisciplinasPendentes().addAll(disciplinasPeriodoValidas);
		return repository.save(planoCurso);
	}

	public PlanoCurso removeDisciplinaPendente(Long planoCursoId, List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		// TODO: validação de tempo minimo por semestre
		PlanoCurso planoCurso = this.findById(planoCursoId);
		Collection<DisciplinaPeriodo> disciplinasPeriodo = new HashSet<>();
		for (DisciplinaPeriodoDTO dpDTO: disciplinasPeriodoDTOS) {
			DisciplinaPeriodo dp = disciplinaPeriodoService.getDisciplinaPeriodoPorPeriodoDisciplinaId(dpDTO.getPeriodo(), dpDTO.getIdDisciplina());
			disciplinasPeriodo.add(dp);
		}
		disciplinasPeriodo.forEach(planoCurso.getDisciplinasPendentes()::remove);
		return repository.save(planoCurso);
	}

	public List<Integer> cargaHorariaPeriodos(PlanoCurso planoCurso, Vinculo vinculo) {
		List<Integer> result = new ArrayList<>(Collections.nCopies(vinculo.getCurso().getPrazoMaximo(), 0));
		for (DisciplinaPeriodo dp: planoCurso.getDisciplinasCursadas()) {
			result.set(dp.getPeriodo() - 1, result.get(dp.getPeriodo() - 1) + dp.getDisciplina().getCh());
		}
		for (DisciplinaPeriodo dp: planoCurso.getDisciplinasPendentes()) {
			result.set(dp.getPeriodo() - 1, result.get(dp.getPeriodo() - 1) + dp.getDisciplina().getCh());
		}
		return result.subList(vinculo.getPeriodoAtualPeriodo()-1, vinculo.getCurso().getPrazoMaximo()-1);
	}

	public PlanoCurso adicionaInteressePes(Long planoCursoId, List<Long> pesIds) {
		PlanoCurso planoCurso = this.findById(planoCursoId);
		Vinculo vinculo = vinculoService.findByPlanoCursoId(planoCurso.getId());
		List<Integer> chs = this.cargaHorariaPeriodos(planoCurso, vinculo);
		List<Pes> pesList = pesService.findByIds(pesIds);
		List<Disciplina> disciplinas = planoCurso.getDisciplinasPendentes().stream()
				.map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toList());
		for (Pes pes : pesList) {
			for (Disciplina d: pes.getDisciplinasObrigatorias()) {
				int minIdx = vinculo.getPeriodoAtualPeriodo();
				for (int i = vinculo.getPeriodoAtualPeriodo()+1; i < chs.size(); ++i) {
					if (chs.get(i) < chs.get(minIdx)) {
						minIdx = i;
					}
				}
				if (!disciplinas.contains(d)) {
					disciplinas.add(d);
					planoCurso.getDisciplinasPendentes().add(new DisciplinaPeriodo(d, minIdx));
					chs.set(minIdx, chs.get(minIdx)+d.getCh());
				}
			}
		}
		planoCurso.getPesInteresse().addAll(pesList);
		return repository.save(planoCurso);
	}

	public PlanoCurso removeInteressePes(Long planoCursoId, List<Long> pesIds) {
		// TODO: verificar se alguns dos PES restantes não teve nenhuma disciplina removida com a remoção dos outros PES
		PlanoCurso planoCurso = this.findById(planoCursoId);
		List<Pes> pesList = pesService.findByIds(pesIds);
		List<DisciplinaPeriodo> to_remove = new ArrayList<>();
		for (Pes pes : pesList) {
			for (Disciplina d: pes.getDisciplinasObrigatorias()) {
				for (DisciplinaPeriodo dp: planoCurso.getDisciplinasPendentes()) {
					if (dp.getDisciplina() == d) {
						to_remove.add(dp);
					}
				}
			}
			to_remove.forEach(planoCurso.getDisciplinasPendentes()::remove);
		}
		pesList.forEach(planoCurso.getPesInteresse()::remove);
		return repository.save(planoCurso);
	}

	public PlanoCurso alterarPlanoCursoEnfase(PlanoCurso planoCurso, Enfase enfase) {
		Set<DisciplinaPeriodo> obrigatoriasEnfase = new HashSet<>(enfase.getDisciplinasObrigatorias());
		Set<Disciplina> disciplinas = planoCurso.getDisciplinasCursadas().stream().map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet());
		Set<DisciplinaPeriodo> pendentes = new HashSet<>();
		for (DisciplinaPeriodo dp: obrigatoriasEnfase) {
			if (!disciplinas.contains(dp.getDisciplina()) && !disciplinaService.checarEquivalencia(disciplinas, dp.getDisciplina())) {
				pendentes.add(dp);
			}
		}
		planoCurso.setDisciplinasPendentes(pendentes);
		return repository.save(planoCurso);
	}
}
