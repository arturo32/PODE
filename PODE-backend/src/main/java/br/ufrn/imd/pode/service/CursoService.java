package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.dto.CursoDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.repository.CursoRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import javax.transaction.Transactional;

@Service
@Transactional
public class CursoService extends GenericService<Curso, CursoDTO, Long> {

	private CursoRepository repository;
	private DisciplinaPeriodoService disciplinaPeriodoService;
	private DisciplinaService disciplinaService;

	@Override
	public CursoDTO convertToDto(Curso curso) {
		return new CursoDTO(curso);
	}

	@Override
	public Curso convertToEntity(CursoDTO cursoDTO) {
		Curso curso = new Curso();
		curso.setId(cursoDTO.getId());
		curso.setNome(cursoDTO.getNome());
		curso.setChm(cursoDTO.getChm());
		curso.setCho(cursoDTO.getCho());
		curso.setChom(cursoDTO.getChom());
		curso.setChcm(cursoDTO.getChcm());
		curso.setChem(cursoDTO.getChem());
		curso.setChminp(cursoDTO.getChminp());
		curso.setChmaxp(cursoDTO.getChmaxp());
		curso.setPrazoMinimo(cursoDTO.getPrazoMinimo());
		curso.setPrazoMaximo(cursoDTO.getPrazoMaximo());
		curso.setPrazoEsperado(cursoDTO.getPrazoEsperado());
		for (DisciplinaPeriodoDTO disciplinaPeriodoDTO : cursoDTO.getDisciplinasObrigatorias()) {
			if (disciplinaPeriodoDTO.getId() != null) {
				curso.getDisciplinasObrigatorias()
						.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO.getId()));
			} else {
				throw new InconsistentEntityException("disciplinaObrigatoria inconsistente");
			}
		}
		for (DisciplinaDTO disciplinaDTO : cursoDTO.getDisciplinasOptativas()) {
			if (disciplinaDTO.getId() != null) {
				curso.getDisciplinasOptativas().add(this.disciplinaService.findById(disciplinaDTO.getId()));
			} else {
				throw new InconsistentEntityException("disciplinaOptativa inconsistente");
			}
		}
		return curso;
	}

	@Override
	protected GenericRepository<Curso, Long> repository() {
		return this.repository;
	}

	public CursoRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(CursoRepository cursoRepository) {
		this.repository = cursoRepository;
	}

	public DisciplinaPeriodoService getDisciplinaPeriodoService() {
		return this.disciplinaPeriodoService;
	}

	@Autowired
	public void setDisciplinaPeriodoService(DisciplinaPeriodoService disciplinaPeriodoService) {
		this.disciplinaPeriodoService = disciplinaPeriodoService;
	}

	public DisciplinaService getDisciplinaService() {
		return this.disciplinaService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	@Override
	public CursoDTO validate(CursoDTO curso) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		/** verifica nome */
		if (curso.getNome() == null || curso.getNome().isEmpty()) {
			exceptionHelper.add("nome inválido");
		}
		/** verifica chm */
		boolean statusChm = false;
		if (curso.getChm() == null || curso.getChm() <= 0) {
			exceptionHelper.add("chm inválido");
		} else {
			statusChm = true;
		}
		/** verifica cho */
		boolean statusCho = false;
		if (curso.getCho() == null || curso.getCho() <= 0) {
			exceptionHelper.add("cho inválido");
		} else {
			statusCho = true;
		}
		/** verifica chom */
		boolean statusChom = false;
		if (curso.getChom() == null || curso.getChom() <= 0) {
			exceptionHelper.add("chom inválido");
		} else {
			statusChom = true;
		}
		/** verifica chcm */
		boolean statusChcm = false;
		if (curso.getChcm() == null || curso.getChcm() <= 0) {
			exceptionHelper.add("chcm inválido");
		} else {
			statusChcm = true;
		}
		/** verifica cho em relação a chm */
		if (statusCho && statusChm) {
			if (curso.getCho() > curso.getChm()) {
				exceptionHelper.add("impossível cho ser maior do que chm");
			}
		}
		/** verifica chom em relação a chm */
		if (statusChom && statusChm) {
			if (curso.getChom() > curso.getChm()) {
				exceptionHelper.add("impossível chom ser maior do que chm");
			}
		}
		/** verifica chcm em relação a chm */
		if (statusChcm && statusChm) {
			if (curso.getChcm() > curso.getChm()) {
				exceptionHelper.add("impossível chcm ser maior do que chm");
			}
		}
		/** verifica a relacao entre cho, chom, chcm e chm */
		if (statusCho && statusChm && statusChom && statusChcm) {
			if ((curso.getCho() + curso.getChom() + curso.getChcm()) != curso.getChm()) {
				exceptionHelper.add("cho, chom e chcm somados deve resultar em chm");
			}
		}
		/** verifica chem */
		if (curso.getChem() == null || curso.getChem() <= 0) {
			exceptionHelper.add("chem inválido");
		}
		/** verifica chminp */
		boolean chminp = false;
		if (curso.getChminp() == null || curso.getChminp() <= 0) {
			exceptionHelper.add("chminp inválido");
		} else {
			chminp = true;
		}
		/** verifica chmaxp */
		boolean chmaxp = false;
		if (curso.getChmaxp() == null || curso.getChmaxp() <= 0) {
			exceptionHelper.add("chmaxp inválido");
		} else {
			chmaxp = true;
		}
		/** verifica a relacao entre chminp e chmaxp */
		if (chminp && chmaxp) {
			if (curso.getChminp() > curso.getChmaxp()) {
				exceptionHelper.add("impossível chminp ser maior do que chmaxp");
			}
		}
		/** verifica prazoMinimo */
		boolean statusPrazoMinimo = false;
		if (curso.getPrazoMinimo() == null || curso.getPrazoMinimo() <= 0) {
			exceptionHelper.add("prazoMinimo inválido");
		} else {
			statusPrazoMinimo = true;
		}
		/** verifica prazoMaximo */
		boolean statusPrazoMaximo = false;
		if (curso.getPrazoMaximo() == null || curso.getPrazoMaximo() <= 0) {
			exceptionHelper.add("prazoMaximo inválido");
		} else {
			statusPrazoMaximo = true;
		}
		/** verifica prazoEsperado */
		boolean statusPrazoEsperado = false;
		if (curso.getPrazoEsperado() == null || curso.getPrazoEsperado() <= 0) {
			exceptionHelper.add("prazoEsperado inválido");
		} else {
			statusPrazoEsperado = true;
		}
		/** verifica a relacao entre prazoMinimo, prazoMaximo e prazoEsperado */
		if (statusPrazoMinimo && statusPrazoMaximo && statusPrazoEsperado) {
			if (curso.getPrazoMinimo() > curso.getPrazoMaximo()) {
				exceptionHelper.add("impossível prazoMinimo ser maior do que prazoMaximo");
			}
			if (curso.getPrazoEsperado() < curso.getPrazoMinimo()) {
				exceptionHelper.add("impossível prazoEsperado ser menor do que prazoMinimo");
			}
			if (curso.getPrazoEsperado() > curso.getPrazoMaximo()) {
				exceptionHelper.add("impossível prazoEsperado ser maior do que prazoMaximo");
			}
		}
		/** verifica disciplinasObrigatorias */
		if (curso.getDisciplinasObrigatorias() != null) {
			Iterator<DisciplinaPeriodo> iterador = curso.getDisciplinasObrigatorias().iterator();
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
		/** verifica disciplinasOptativas */
		if (curso.getDisciplinasOptativas() != null) {
			Iterator<Disciplina> iterador = curso.getDisciplinasOptativas().iterator();
			while (iterador.hasNext()) {
				Disciplina disciplina = iterador.next();
				if (disciplina.getId() == null || disciplina.getId() < 0) {
					exceptionHelper.add("disciplinaOptativa inconsistente");
				} else {
					try {
						this.disciplinaService.findById(disciplina.getId());
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaOptativa(id=" + disciplina.getId() + ") inexistente");
					}
				}
			}
		}
		/** verifica se existe exceçao */
		if (exceptionHelper.getMessage().isEmpty()) {
			return curso;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

}
