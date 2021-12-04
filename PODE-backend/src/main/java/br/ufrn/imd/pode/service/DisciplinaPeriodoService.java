package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.repository.DisciplinaPeriodoRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DisciplinaPeriodoService extends GenericService<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaPeriodoRepository repository;
	private DisciplinaService disciplinaService;

	@Override
	public DisciplinaPeriodoDTO convertToDto(DisciplinaPeriodo disciplinaPeriodo) {
		return new DisciplinaPeriodoDTO(disciplinaPeriodo);
	}

	@Override
	public DisciplinaPeriodo convertToEntity(DisciplinaPeriodoDTO disciplinaPeriodoDTO) {
		DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo();
		disciplinaPeriodo.setId(disciplinaPeriodoDTO.getId());
		if (disciplinaPeriodoDTO.getDisciplina().getId() != null) {
			disciplinaPeriodo
					.setDisciplina(this.disciplinaService.findById(disciplinaPeriodoDTO.getDisciplina().getId()));
		} else {
			throw new InconsistentEntityException("disciplina inconsistente");
		}
		disciplinaPeriodo.setPeriodo(disciplinaPeriodoDTO.getPeriodo());
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

	@Override
	public DisciplinaPeriodoDTO validate(DisciplinaPeriodoDTO disciplinaPeriodo) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplina
		if (disciplinaPeriodo.getDisciplina().getId() == null || disciplinaPeriodo.getDisciplina().getId() < 0) {
			exceptionHelper.add("disciplina inconsistente");
		} else {
			try {
				this.disciplinaService.findById(disciplinaPeriodo.getDisciplina().getId());
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
}
