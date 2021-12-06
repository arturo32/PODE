package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanoCursoService extends GenericService<PlanoCurso, PlanoCursoDTO, Long> {

	private PlanoCursoRepository repository;
	private DisciplinaPeriodoService disciplinaPeriodoService;
	private DisciplinaService disciplinaService;
	private PesService pesService;

	@Override
	public PlanoCursoDTO convertToDto(PlanoCurso planoCurso) {
		return new PlanoCursoDTO(planoCurso);
	}

	@Override
	public PlanoCurso convertToEntity(PlanoCursoDTO dto) {
		PlanoCurso planoCurso = new PlanoCurso();

		//Se for uma edição
		if (dto.getId() != null) {
			planoCurso = this.findById(planoCurso.getId());
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
		List<DisciplinaPeriodo> disciplinasPeriodo = disciplinaPeriodoService.convertToEntityList(disciplinasPeriodoDTOS);
		planoCurso.getDisciplinasCursadas().addAll(disciplinasPeriodo);

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
		List<DisciplinaPeriodo> disciplinasPeriodo = disciplinaPeriodoService.convertToEntityList(disciplinasPeriodoDTOS);
		planoCurso.getDisciplinasPendentes().addAll(disciplinasPeriodo);
		return repository.save(planoCurso);
	}

}
