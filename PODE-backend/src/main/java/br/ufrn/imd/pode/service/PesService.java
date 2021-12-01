package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.PesDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.PesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import javax.transaction.Transactional;

@Service
@Transactional
public class PesService extends GenericService<Pes, PesDTO, Long> {

	private PesRepository repository;
	private DisciplinaService disciplinaService;

	@Override
	public PesDTO convertToDto(Pes pes) {
		return new PesDTO(pes);
	}

	@Override
	public Pes convertToEntity(PesDTO pesDTO) {
		Pes pes = new Pes();
		pes.setId(pesDTO.getId());
		pes.setNome(pesDTO.getNome());
		pes.setChm(pesDTO.getChm());
		pes.setCho(pesDTO.getCho());
		for (DisciplinaDTO disciplinaDTO : pesDTO.getDisciplinasObrigatorias()) {
			if (disciplinaDTO.getId() != null) {
				pes.getDisciplinasObrigatorias().add(this.disciplinaService.findById(disciplinaDTO.getId()));
			} else {
				throw new InconsistentEntityException("disciplinaObrigatoria inconsistente");
			}
		}
		for (DisciplinaDTO disciplinaDTO : pesDTO.getDisciplinasOptativas()) {
			if (disciplinaDTO.getId() != null) {
				pes.getDisciplinasOptativas().add(this.disciplinaService.findById(disciplinaDTO.getId()));
			} else {
				throw new InconsistentEntityException("disciplinaOptativa inconsistente");
			}
		}
		return pes;
	}

	@Override
	protected GenericRepository<Pes, Long> repository() {
		return this.repository;
	}

	public PesRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(PesRepository pesRepository) {
		this.repository = pesRepository;
	}

	public DisciplinaService getDisciplinaService() {
		return this.disciplinaService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	public Pes salvar(Pes pes) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		/** verifica nome */
		if (pes.getNome() == null || pes.getNome().isEmpty()) {
			exceptionHelper.add("nome inválido");
		}
		/** verifica chm */
		boolean statusChm = false;
		if (pes.getChm() == null || pes.getChm() <= 0) {
			exceptionHelper.add("chm inválido");
		} else {
			statusChm = true;
		}
		/** verifica cho */
		boolean statusCho = false;
		if (pes.getCho() == null || pes.getCho() <= 0) {
			exceptionHelper.add("cho inválido");
		} else {
			statusCho = true;
		}
		/** verifica cho em relação a chm */
		if (statusCho && statusChm) {
			if (pes.getCho() > pes.getChm()) {
				exceptionHelper.add("impossível cho ser maior do que chm");
			}
		}
		/** verifica disciplinasObrigatorias */
		if (pes.getDisciplinasObrigatorias() != null) {
			Iterator<Disciplina> iterador = pes.getDisciplinasObrigatorias().iterator();
			while (iterador.hasNext()) {
				Disciplina disciplina = iterador.next();
				if (disciplina.getId() == null || disciplina.getId() < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.disciplinaService.findById(disciplina.getId());
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaObrigatoria(id=" + disciplina.getId() + ") inexistente");
					}
				}
			}
		}
		/** verifica disciplinasOptativas */
		if (pes.getDisciplinasOptativas() != null) {
			Iterator<Disciplina> iterador = pes.getDisciplinasOptativas().iterator();
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
			return this.save(pes);
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}
}
