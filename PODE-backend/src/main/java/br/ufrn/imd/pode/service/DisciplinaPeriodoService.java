package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.repository.DisciplinaPeriodoRepository;
import br.ufrn.imd.pode.repository.GenericRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DisciplinaPeriodoService extends GenericService<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaPeriodoRepository repository;
	private DisciplinaService disciplinaService;
	private PlanoCursoService planoCursoService;

	@Override
	public DisciplinaPeriodoDTO convertToDto(DisciplinaPeriodo disciplinaPeriodo) {
		return new DisciplinaPeriodoDTO(disciplinaPeriodo);
	}

	@Override
	public DisciplinaPeriodo convertToEntity(DisciplinaPeriodoDTO dto) {
		DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo();

		//Se for uma edição
		if (dto.getId() != null) {
			disciplinaPeriodo = this.findById(dto.getId());
		}

		disciplinaPeriodo.setId(dto.getId());

		if (dto.getIdDisciplina() == null) {
			throw new InconsistentEntityException("disciplina inconsistente");
		}
		try {
			disciplinaPeriodo
					.setDisciplina(this.disciplinaService.findById(dto.getIdDisciplina()));
		} catch (EntityNotFoundException entityNotFoundException){
			throw new InconsistentEntityException("disciplina inconsistente");
		}

		if (dto.getPeriodo() != null) {
			disciplinaPeriodo.setPeriodo(dto.getPeriodo());
		}

		return disciplinaPeriodo;
	}

	@Override
	protected GenericRepository<DisciplinaPeriodo, Long> repository() {
		return this.repository;
	}

	public DisciplinaPeriodoRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(DisciplinaPeriodoRepository disciplinaPeriodoRepository) {
		this.repository = disciplinaPeriodoRepository;
	}

	public DisciplinaService getDisciplinaService() {
		return disciplinaService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	public PlanoCursoService getPlanoCursoService() {
		return planoCursoService;
	}

	@Autowired
	public void setPlanoCursoService(PlanoCursoService planoCursoService) {
		this.planoCursoService = planoCursoService;
	}

	@Override
	public DisciplinaPeriodoDTO validate(DisciplinaPeriodoDTO disciplinaPeriodo) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplina
		if (disciplinaPeriodo.getIdDisciplina()== null || disciplinaPeriodo.getIdDisciplina() < 0) {
			exceptionHelper.add("disciplina inconsistente");
		} else {
			try {
				this.disciplinaService.findById(disciplinaPeriodo.getIdDisciplina());
			} catch (EntityNotFoundException entityNotFoundException) {
				exceptionHelper.add("disciplina inexistente");
			}
		}

		//Verifica período
		if (disciplinaPeriodo.getPeriodo() == null || disciplinaPeriodo.getPeriodo() <= 0) {
			exceptionHelper.add("periodo inválido");
		}
		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return disciplinaPeriodo;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

	public List<DisciplinaPeriodo> disciplinaPendentesPlanoCurso(Long planoCursoId) {
		PlanoCurso planoCurso = planoCursoService.findById(planoCursoId);
		List<DisciplinaPeriodo> pendentes = new ArrayList<>(planoCurso.getDisciplinasPendentes());
		pendentes.sort((d1, d2) -> -d2.getPeriodo().compareTo(d1.getPeriodo()));
		return pendentes;
	}

	public List<DisciplinaPeriodo> disciplinaCursadasPlanoCurso(Long planoCursoId) {
		PlanoCurso planoCurso = planoCursoService.findById(planoCursoId);
		List<DisciplinaPeriodo> pendentes = new ArrayList<>(planoCurso.getDisciplinasCursadas());
		pendentes.sort((d1, d2) -> -d2.getPeriodo().compareTo(d1.getPeriodo()));
		return pendentes;
	}

	public DisciplinaPeriodo getDisciplinaPeriodoPorPeriodoDisciplinaId(Integer periodo, Long disciplinaId) {
		Optional<DisciplinaPeriodo> opt = repository.findByAtivoIsTrueAndPeriodoAndDisciplina_Id(periodo, disciplinaId);
		return opt.orElseGet(() -> repository.save(new DisciplinaPeriodo(disciplinaService.findById(disciplinaId), periodo)));
	}
}
