package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.PesDTO;
import br.ufrn.imd.pode.model.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.PlanoCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import javax.transaction.Transactional;

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
	public PlanoCurso convertToEntity(PlanoCursoDTO planoCursoDTO) {
		PlanoCurso planoCurso = new PlanoCurso();
		planoCurso.setId(planoCursoDTO.getId());
		for (DisciplinaPeriodoDTO disciplinaPeriodoDTO : planoCursoDTO.getDisciplinasCursadas()) {
			if (disciplinaPeriodoDTO.getId() != null) {
				planoCurso.getDisciplinasCursadas().add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO.getId()));
			} else {
				throw new InconsistentEntityException("disciplinaCursada inconsistente");
			}
		}
		for (DisciplinaPeriodoDTO disciplinaPeriodoDTO : planoCursoDTO.getDisciplinasPendentes()) {
			if (disciplinaPeriodoDTO.getId() != null) {
				planoCurso.getDisciplinasPendentes().add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO.getId()));
			} else {
				throw new InconsistentEntityException("disciplinaPendente inconsistente");
			}
		}
		for (PesDTO desDTO : planoCursoDTO.getPesInteresse()) {
			if (desDTO.getId() != null) {
				planoCurso.getPesInteresse().add(this.pesService.findById(desDTO.getId()));
			} else {
				throw new InconsistentEntityException("pes inconsistente");
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

	public PlanoCurso salvar(PlanoCurso planoCurso) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		/** verifica disciplinasCursadas */
		if (planoCurso.getDisciplinasCursadas() != null) {
			Iterator<DisciplinaPeriodo> iterador = planoCurso.getDisciplinasCursadas().iterator();
			while (iterador.hasNext()) {
				DisciplinaPeriodo disciplinaPeriodo = iterador.next();
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
		/** verifica disciplinasPendentes */
		if (planoCurso.getDisciplinasPendentes() != null) {
			Iterator<DisciplinaPeriodo> iterador = planoCurso.getDisciplinasPendentes().iterator();
			while (iterador.hasNext()) {
				DisciplinaPeriodo disciplinaPeriodo = iterador.next();
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
		/** verifica pesInteresse */
		if (planoCurso.getPesInteresse() != null) {
			Iterator<Pes> iterador = planoCurso.getPesInteresse().iterator();
			while (iterador.hasNext()) {
				Pes pes = iterador.next();
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
		/** verifica se existe exceçao */
		if (exceptionHelper.getMessage().isEmpty()) {
			return this.save(planoCurso);
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

}
