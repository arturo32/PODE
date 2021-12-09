package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.PrerequisitosNaoAtendidosException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.*;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.PesDTO;
import br.ufrn.imd.pode.model.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.PlanoCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;

@Service
@Transactional
public class PlanoCursoService extends GenericService<PlanoCurso, PlanoCursoDTO, Long> {

	private PlanoCursoRepository repository;
	private DisciplinaPeriodoService disciplinaPeriodoService;
	private DisciplinaService disciplinaService;
	private PesService pesService;
	private VinculoService vinculoService;
	private EnfaseService enfaseService;

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
					throw new InconsistentEntityException("disciplinaCursada inconsistente");
				}

				try {
					planoCurso.getDisciplinasCursadas()
							.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("disciplinaCursada inconsistente");
				}
			}
		}

		if (dto.getIdDisciplinasPendentes() != null){
			planoCurso.setDisciplinasPendentes(new HashSet<>());
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodoDTO== null) {
					throw new InconsistentEntityException("disciplinaPendente inconsistente");
				}

				try {
					planoCurso.getDisciplinasPendentes()
							.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("disciplinaPendente inconsistente");
				}
			}
		}

		if (dto.getIdPesInteresse() != null) {
			planoCurso.setPesInteresse(new HashSet<>());
			for (Long desDTO : dto.getIdPesInteresse()) {
				if (desDTO == null) {
					throw new InconsistentEntityException("pes inconsistente");
				}

				try {
					planoCurso.getPesInteresse()
							.add(this.pesService.findById(desDTO));
				}
				catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("pes inconsistente");
				}
			}
		}

		return planoCurso;
	}

	@Override
	protected GenericRepository<PlanoCurso, Long> repository() {
		return this.repository;
	}

	public PlanoCursoRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(PlanoCursoRepository planoCursoRepository) {
		this.repository = planoCursoRepository;
	}

	public DisciplinaPeriodoService getDisciplinaPeriodoService() {
		return this.disciplinaPeriodoService;
	}

	@Autowired
	public void setDisciplinaPeriodoService(DisciplinaPeriodoService disciplinaPeriodoService) {
		this.disciplinaPeriodoService = disciplinaPeriodoService;
	}

	public PesService getPesService() {
		return this.pesService;
	}

	@Autowired
	public void setPesService(PesService pesService) {
		this.pesService = pesService;
	}

	public DisciplinaService getDisciplinaService() {
		return disciplinaService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	public VinculoService getVinculoService() {
		return vinculoService;
	}

	@Autowired
	public void setVinculoService(VinculoService vinculoService) {
		this.vinculoService = vinculoService;
	}

	public EnfaseService getEnfaseService() {
		return enfaseService;
	}

	@Autowired
	public void setEnfaseService(EnfaseService enfaseService) {
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
					} catch (EntityNotFoundException entityNotFoundException) {
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
					} catch (EntityNotFoundException entityNotFoundException) {
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
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("pes(id=" + pes + ") inexistente");
					}
				}
			}
		}

		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return planoCurso;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
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
		PlanoCurso planoCurso = this.findById(planoCursoId);
		Collection<DisciplinaPeriodo> disciplinasPeriodo = new HashSet<>();

		for (DisciplinaPeriodoDTO dp: disciplinasPeriodoDTOS) {
			Disciplina d =disciplinaService.findById(dp.getIdDisciplina());
			if (disciplinaService.checarPrerequisitos(planoCurso.getDisciplinasCursadas()
					.stream().map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet()), d)) {
				disciplinasPeriodo.add(disciplinaPeriodoService.getDisciplinaPeriodoPorPeriodoDisciplinaId(dp.getPeriodo(), dp.getIdDisciplina()));
			} else {
				// TODO: capturar todas as disciplinas sem prerequisitos atendidos e lançar uma excessão no final
				throw new PrerequisitosNaoAtendidosException("Disciplina de código '" + d.getCodigo() + "' não teve os prerequisitos atendidos");
			}

		}
		planoCurso.getDisciplinasCursadas().addAll(disciplinasPeriodo);

		// TODO adicionar validação de que as disciplinas cursadas são equivalentes a alguma pendente do plano de curso
		List<Disciplina> disciplinas = disciplinasPeriodo.stream().map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toList());
		Set<DisciplinaPeriodo> pendentes = new HashSet<>();
		for (DisciplinaPeriodo dp: planoCurso.getDisciplinasPendentes()) {
			if (!disciplinas.contains(dp.getDisciplina())) {
				pendentes.add(dp);
			}
		}
		planoCurso.setDisciplinasPendentes(pendentes);
		return repository.save(planoCurso);
	}

	public PlanoCurso adicionaDisciplinaPendente(Long planoCursoId, List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		// TODO: validação de tempo maximo por semestre
		PlanoCurso planoCurso = this.findById(planoCursoId);
		Collection<DisciplinaPeriodo> disciplinasPeriodo = disciplinaPeriodoService.convertToEntityList(disciplinasPeriodoDTOS);
		planoCurso.getDisciplinasPendentes().addAll(disciplinasPeriodo);
		return repository.save(planoCurso);
	}

	public PlanoCurso removeDisciplinaPendente(Long planoCursoId, List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		// TODO: validação de tempo minimo por semestre
		PlanoCurso planoCurso = this.findById(planoCursoId);
		Collection<DisciplinaPeriodo> disciplinasPeriodo = disciplinaPeriodoService.convertToEntityList(disciplinasPeriodoDTOS);
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
		return result.subList(vinculo.getPeriodoAtual()-1, vinculo.getCurso().getPrazoMaximo()-1);
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
				int minIdx = vinculo.getPeriodoAtual();
				for (int i = vinculo.getPeriodoAtual()+1; i < chs.size(); ++i) {
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
		obrigatoriasEnfase.removeAll(planoCurso.getDisciplinasCursadas());
		// TODO remover as equivalentes também
		planoCurso.setDisciplinasPendentes(obrigatoriasEnfase);
		return repository.save(planoCurso);
	}
}
