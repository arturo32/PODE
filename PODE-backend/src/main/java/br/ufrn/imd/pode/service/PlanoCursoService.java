package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.PesDTO;
import br.ufrn.imd.pode.model.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.PlanoCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
@Transactional
public class PlanoCursoService extends GenericService<PlanoCurso, PlanoCursoDTO, Long> {

	private PlanoCursoRepository repository;
	private DisciplinaPeriodoService disciplinaPeriodoService;
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
		if (dto.getDisciplinasCursadas() != null) {
			planoCurso.setDisciplinasCursadas(new HashSet<>());
			for (DisciplinaPeriodoDTO disciplinaPeriodoDTO : dto.getDisciplinasCursadas()) {
				if (disciplinaPeriodoDTO.getId() == null) {
					throw new InconsistentEntityException("disciplinaCursada inconsistente");
				}

				try {
					planoCurso.getDisciplinasCursadas()
							.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO.getId()));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("disciplinaCursada inconsistente");
				}
			}
		}

		if (dto.getDisciplinasPendentes() != null){
			planoCurso.setDisciplinasPendentes(new HashSet<>());
			for (DisciplinaPeriodoDTO disciplinaPeriodoDTO : dto.getDisciplinasPendentes()) {
				if (disciplinaPeriodoDTO.getId() == null) {
					throw new InconsistentEntityException("disciplinaPendente inconsistente");
				}

				try {
					planoCurso.getDisciplinasPendentes()
							.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO.getId()));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("disciplinaPendente inconsistente");
				}
			}
		}

		if (dto.getPesInteresse() != null) {
			planoCurso.setPesInteresse(new HashSet<>());
			for (PesDTO desDTO : dto.getPesInteresse()) {
				if (desDTO.getId() == null) {
					throw new InconsistentEntityException("pes inconsistente");
				}

				try {
					planoCurso.getPesInteresse()
							.add(this.pesService.findById(desDTO.getId()));
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

	@Override
	public PlanoCursoDTO validate(PlanoCursoDTO planoCurso) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplinasCursadas
		if (planoCurso.getDisciplinasCursadas() != null) {
			for (DisciplinaPeriodoDTO disciplinaPeriodo : planoCurso.getDisciplinasCursadas()) {
				if (disciplinaPeriodo.getId() == null || disciplinaPeriodo.getId() < 0) {
					exceptionHelper.add("disciplinaCursada inconsistente");
				} else {
					try {
						this.disciplinaPeriodoService.findById(disciplinaPeriodo.getId());
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaCursada(id=" + disciplinaPeriodo.getId() + ") inexistente");
					}
				}
			}
		}

		//Verifica disciplinasPendentes
		if (planoCurso.getDisciplinasPendentes() != null) {
			for (DisciplinaPeriodoDTO disciplinaPeriodo : planoCurso.getDisciplinasPendentes()) {
				if (disciplinaPeriodo.getId() == null || disciplinaPeriodo.getId() < 0) {
					exceptionHelper.add("disciplinaPendente inconsistente");
				} else {
					try {
						this.disciplinaPeriodoService.findById(disciplinaPeriodo.getId());
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaPendente(id=" + disciplinaPeriodo.getId() + ") inexistente");
					}
				}
			}
		}

		//Verifica pesInteresse
		if (planoCurso.getPesInteresse() != null) {
			for (PesDTO pes : planoCurso.getPesInteresse()) {
				if (pes.getId() == null || pes.getId() < 0) {
					exceptionHelper.add("disciplinaPendente inconsistente");
				} else {
					try {
						this.pesService.findById(pes.getId());
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("pes(id=" + pes.getId() + ") inexistente");
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

}
