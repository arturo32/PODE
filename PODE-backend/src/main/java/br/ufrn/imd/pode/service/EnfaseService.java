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
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

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
	public Enfase convertToEntity(EnfaseDTO dto) {
		Enfase enfase = new Enfase();

		//Se for uma edição
		if (dto.getId() != null) {
			enfase = this.findById(dto.getId());
		}

		enfase.setId(dto.getId());
		if(dto.getNome() != null){
			enfase.setNome(dto.getNome());
		}

		if (dto.getIdCurso() == null) {
			throw new InconsistentEntityException("curso inconsistente");
		}
		try {
			enfase.setCurso(this.cursoService.findById(dto.getIdCurso()));
		} catch (EntityNotFoundException entityNotFoundException){
			throw new InconsistentEntityException("curso inconsistente");
		}

		if (dto.getIdDisciplinasObrigatorias() != null) {
			enfase.setDisciplinasObrigatorias(new HashSet<>());
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasObrigatorias()) {
				if (disciplinaPeriodoDTO== null) {
					throw new InconsistentEntityException("disciplinaPeriodo inconsistente");
				}

				try {
					enfase.getDisciplinasObrigatorias()
							.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO));
				} catch (EntityNotFoundException entityNotFoundException){
					throw new InconsistentEntityException("disciplinaPeriodo inconsistente");
				}
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

		//Verifica nome
		if (StringUtils.isEmpty(enfase.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		//Verifica curso
		if (enfase.getIdCurso() == null || enfase.getIdCurso() < 0) {
			exceptionHelper.add("curso inconsistente");
		} else {
			try {
				this.cursoService.findById(enfase.getIdCurso());
			} catch (EntityNotFoundException entityNotFoundException) {
				exceptionHelper.add("curso inexistente");
			}
		}

		//Verifica disciplinas obrigatórias
		if (enfase.getIdDisciplinasObrigatorias() != null) {
			for (Long disciplinaPeriodo : enfase.getIdDisciplinasObrigatorias()) {
				if (disciplinaPeriodo == null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.disciplinaPeriodoService.findById(disciplinaPeriodo);
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaObrigatoria(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return enfase;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

}
