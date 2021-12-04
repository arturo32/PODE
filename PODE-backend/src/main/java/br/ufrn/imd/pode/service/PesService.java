package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.PesDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.PesRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			if (disciplinaDTO.getId() == null) {
				throw new InconsistentEntityException("disciplinaObrigatoria inconsistente");
			}

			try {
				pes.getDisciplinasObrigatorias()
						.add(this.disciplinaService.findById(disciplinaDTO.getId()));
			} catch (EntityNotFoundException entityNotFoundException){
				throw new InconsistentEntityException("disciplinaObrigatoria inconsistente");
			}
		}

		for (DisciplinaDTO disciplinaDTO : pesDTO.getDisciplinasOptativas()) {
			if (disciplinaDTO.getId() == null) {
				throw new InconsistentEntityException("disciplinaOptativa inconsistente");
			}
			try {
				pes.getDisciplinasOptativas()
						.add(this.disciplinaService.findById(disciplinaDTO.getId()));
			} catch (EntityNotFoundException entityNotFoundException){
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

	@Override
	public PesDTO validate(PesDTO pes) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica nome
		if (StringUtils.isEmpty(pes.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		//Verifica chm
		boolean statusChm = false;
		if (pes.getChm() == null || pes.getChm() <= 0) {
			exceptionHelper.add("chm inválido");
		} else {
			statusChm = true;
		}

		//Verifica cho
		boolean statusCho = false;
		if (pes.getCho() == null || pes.getCho() <= 0) {
			exceptionHelper.add("cho inválido");
		} else {
			statusCho = true;
		}

		//Verifica cho em relação a chm
		if (statusCho && statusChm) {
			if (pes.getCho() > pes.getChm()) {
				exceptionHelper.add("impossível cho ser maior do que chm");
			}
		}

		//Verifica disciplinasObrigatorias
		if (pes.getDisciplinasObrigatorias() != null) {
			for (DisciplinaDTO disciplina : pes.getDisciplinasObrigatorias()) {
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

		//Verifica disciplinasOptativas
		if (pes.getDisciplinasOptativas() != null) {
			for (DisciplinaDTO disciplina : pes.getDisciplinasOptativas()) {
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

		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return pes;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}
}
