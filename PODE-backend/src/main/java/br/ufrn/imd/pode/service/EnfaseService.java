package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.EnfaseDTO;
import br.ufrn.imd.pode.repository.EnfaseRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if (enfaseDTO.getCurso().getId() == null) {
			throw new InconsistentEntityException("curso inconsistente");
		}

		try{
			enfase.setCurso(this.cursoService.findById(enfaseDTO.getCurso().getId()));
		} catch (EntityNotFoundException entityNotFoundException){
			throw new InconsistentEntityException("curso inconsistente");
		}

		for (DisciplinaPeriodoDTO disciplinaPeriodoDTO : enfaseDTO.getDisciplinasObrigatorias()) {
			if (disciplinaPeriodoDTO.getId() == null) {
				throw new InconsistentEntityException("disciplinaPeriodo inconsistente");
			}

			try {
				enfase.getDisciplinasObrigatorias()
						.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO.getId()));
			} catch (EntityNotFoundException entityNotFoundException){
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

	@Override
	public EnfaseDTO validate(EnfaseDTO enfase) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		/* verifica nome */
		if (StringUtils.isEmpty(enfase.getNome())) {
			exceptionHelper.add("nome inválido");
		}
		/* verifica curso */
		if (enfase.getCurso().getId() == null || enfase.getCurso().getId() < 0) {
			exceptionHelper.add("curso inconsistente");
		} else {
			try {
				this.cursoService.findById(enfase.getCurso().getId());
			} catch (EntityNotFoundException entityNotFoundException) {
				exceptionHelper.add("curso inexistente");
			}
		}
		/* verifica disciplinas obrigatorias */
		if (enfase.getDisciplinasObrigatorias() != null) {
			for (DisciplinaPeriodoDTO disciplinaPeriodo : enfase.getDisciplinasObrigatorias()) {
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
		/* verifica se existe exceçao */
		if (exceptionHelper.getMessage().isEmpty()) {
			return enfase;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

}
