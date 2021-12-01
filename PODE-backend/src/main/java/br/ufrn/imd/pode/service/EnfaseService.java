package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.EnfaseDTO;
import br.ufrn.imd.pode.repository.EnfaseRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import javax.transaction.Transactional;

@Service
@Transactional
public class EnfaseService extends GenericService<Enfase, EnfaseDTO, Long> {

	private EnfaseRepository repository;
	private CursoService cursoService;
	private DisciplinaPeriodoService disciplinaPeriodoService;

	@Override
	public EnfaseDTO convertToDto(Enfase enfase) {
		return new EnfaseDTO(enfase);
	}

	@Override
	public Enfase convertToEntity(EnfaseDTO enfaseDTO) {
		Enfase enfase = new Enfase();
		enfase.setId(enfaseDTO.getId());
		enfase.setNome(enfaseDTO.getNome());
		if (enfaseDTO.getCurso().getId() != null) {
			enfase.setCurso(this.cursoService.findById(enfaseDTO.getCurso().getId()));
		} else {
			throw new InconsistentEntityException("curso inconsistente");
		}
		for (DisciplinaPeriodoDTO disciplinaPeriodoDTO : enfaseDTO.getDisciplinasObrigatorias()) {
			if (disciplinaPeriodoDTO.getId() != null) {
				enfase.getDisciplinasObrigatorias()
						.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO.getId()));
			} else {
				throw new InconsistentEntityException("disciplinaPeriodo inconsistente");
			}
		}
		return enfase;
	}

	@Override
	protected GenericRepository<Enfase, Long> repository() {
		return this.repository;
	}

	public EnfaseRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(EnfaseRepository enfaseRepository) {
		this.repository = enfaseRepository;
	}

	public CursoService getCursoService() {
		return this.cursoService;
	}

	@Autowired
	public void setCursoService(CursoService cursoService) {
		this.cursoService = cursoService;
	}

	public DisciplinaPeriodoService getDisciplinaPeriodoService() {
		return this.disciplinaPeriodoService;
	}

	@Autowired
	public void setDisciplinaPeriodoService(DisciplinaPeriodoService disciplinaPeriodoService) {
		this.disciplinaPeriodoService = disciplinaPeriodoService;
	}

	public Enfase salvar(Enfase enfase) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		/** verifica nome */
		if (enfase.getNome() == null || enfase.getNome().isEmpty()) {
			exceptionHelper.add("nome inválido");
		}
		/** verifica curso */
		if (enfase.getCurso().getId() == null || enfase.getCurso().getId() < 0) {
			exceptionHelper.add("curso inconsistente");
		} else {
			try {
				this.cursoService.findById(enfase.getCurso().getId());
			} catch (EntityNotFoundException entityNotFoundException) {
				exceptionHelper.add("curso inexistente");
			}
		}
		/** verifica disciplinas obrigatorias */
		if (enfase.getDisciplinasObrigatorias() != null) {
			Iterator<DisciplinaPeriodo> iterador = enfase.getDisciplinasObrigatorias().iterator();
			while (iterador.hasNext()) {
				DisciplinaPeriodo disciplinaPeriodo = iterador.next();
				if (disciplinaPeriodo.getId() == null || disciplinaPeriodo.getId() < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.disciplinaPeriodoService.findById(disciplinaPeriodo.getId());
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaObrigatoria(id=" + disciplinaPeriodo.getId() + ") inexistente");
					}
				}
			}
		}
		/** verifica se existe exceçao */
		if (exceptionHelper.getMessage().isEmpty()) {
			return this.save(enfase);
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

}
