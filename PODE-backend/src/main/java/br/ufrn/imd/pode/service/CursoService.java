package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.exception.ValidationException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.dto.CursoDTO;
import br.ufrn.imd.pode.repository.CursoRepository;
import br.ufrn.imd.pode.repository.GenericRepository;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

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
	public Curso convertToEntity(CursoDTO dto) {
		Curso curso = new Curso();

		//Se for uma edição
		if (dto.getId() != null){
			curso = this.findById(dto.getId());
		}

		curso.setId(dto.getId());
		if (dto.getNome() != null){
			curso.setNome(dto.getNome());
		}
		if (dto.getChm() != null){
			curso.setChm(dto.getChm());
		}
		if (dto.getCho() != null){
			curso.setCho(dto.getCho());
		}
		if (dto.getChom() != null){
			curso.setChom(dto.getChom());
		}
		if (dto.getChcm() != null){
			curso.setChcm(dto.getChcm());
		}
		if (dto.getChem() != null){
			curso.setChem(dto.getChem());
		}
		if (dto.getChminp() != null){
			curso.setChminp(dto.getChminp());
		}
		if (dto.getChmaxp() != null){
			curso.setChmaxp(dto.getChmaxp());
		}
		if (dto.getPrazoMinimo() != null){
			curso.setPrazoMinimo(dto.getPrazoMinimo());
		}
		if (dto.getPrazoMaximo() != null){
			curso.setPrazoMaximo(dto.getPrazoMaximo());
		}
		if (dto.getPrazoEsperado() != null){
			curso.setPrazoEsperado(dto.getPrazoEsperado());
		}

		if (dto.getIdDisciplinasObrigatorias() != null) {
			curso.setDisciplinasObrigatorias(new HashSet<>());
			for (Long idDisciplinaPeriodo : dto.getIdDisciplinasObrigatorias()) {
				if (idDisciplinaPeriodo == null) {
					throw new InconsistentEntityException("disciplinaObrigatoria inconsistente");
				}

				try{
					curso.getDisciplinasObrigatorias()
							.add(this.disciplinaPeriodoService.findById(idDisciplinaPeriodo));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("disciplinaObrigatoria inconsistente");
				}
			}
		}

		if (dto.getIdDisciplinasOptativas() != null) {
			curso.setDisciplinasOptativas(new HashSet<>());
			for (Long idDisciplina : dto.getIdDisciplinasOptativas()) {
				if (idDisciplina == null) {
					throw new InconsistentEntityException("disciplinaOptativa inconsistente");
				}

				try {
					curso.getDisciplinasOptativas()
							.add(this.disciplinaService.findById(idDisciplina));
				} catch (EntityNotFoundException entityNotFoundException){
					throw new InconsistentEntityException("disciplinaOptativa inconsistente");
				}
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

		//Verifica nome
		if (StringUtils.isEmpty(curso.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		//Verifica chm
		boolean statusChm = false;
		if (curso.getChm() == null || curso.getChm() <= 0) {
			exceptionHelper.add("chm inválido");
		} else {
			statusChm = true;
		}

		//Verifica cho
		boolean statusCho = false;
		if (curso.getCho() == null || curso.getCho() <= 0) {
			exceptionHelper.add("cho inválido");
		} else {
			statusCho = true;
		}

		//Verifica chom
		boolean statusChom = false;
		if (curso.getChom() == null || curso.getChom() <= 0) {
			exceptionHelper.add("chom inválido");
		} else {
			statusChom = true;
		}

		//Verifica chcm
		boolean statusChcm = false;
		if (curso.getChcm() == null || curso.getChcm() <= 0) {
			exceptionHelper.add("chcm inválido");
		} else {
			statusChcm = true;
		}

		//Verifica cho em relação a chm
		if (statusCho && statusChm) {
			if (curso.getCho() > curso.getChm()) {
				exceptionHelper.add("impossível cho ser maior do que chm");
			}
		}

		//Verifica chom em relação a chm
		if (statusChom && statusChm) {
			if (curso.getChom() > curso.getChm()) {
				exceptionHelper.add("impossível chom ser maior do que chm");
			}
		}

		//Verifica chcm em relação a chm
		if (statusChcm && statusChm) {
			if (curso.getChcm() > curso.getChm()) {
				exceptionHelper.add("impossível chcm ser maior do que chm");
			}
		}

		//Verifica a relação entre cho, chom, chcm e chm
		if (statusCho && statusChm && statusChom && statusChcm) {
			if ((curso.getCho() + curso.getChom() + curso.getChcm()) != curso.getChm()) {
				exceptionHelper.add("cho, chom e chcm somados deve resultar em chm");
			}
		}

		//Verifica chem
		if (curso.getChem() == null || curso.getChem() <= 0) {
			exceptionHelper.add("chem inválido");
		}

		//Verifica chminp
		boolean chminp = false;
		if (curso.getChminp() == null || curso.getChminp() <= 0) {
			exceptionHelper.add("chminp inválido");
		} else {
			chminp = true;
		}

		//Verifica chmaxp
		boolean chmaxp = false;
		if (curso.getChmaxp() == null || curso.getChmaxp() <= 0) {
			exceptionHelper.add("chmaxp inválido");
		} else {
			chmaxp = true;
		}

		//Verifica a relação entre chminp e chmaxp
		if (chminp && chmaxp) {
			if (curso.getChminp() > curso.getChmaxp()) {
				exceptionHelper.add("impossível chminp ser maior do que chmaxp");
			}
		}
		//Verifica prazoMinimo
		boolean statusPrazoMinimo = false;
		if (curso.getPrazoMinimo() == null || curso.getPrazoMinimo() <= 0) {
			exceptionHelper.add("prazoMinimo inválido");
		} else {
			statusPrazoMinimo = true;
		}

		//Verifica prazoMaximo
		boolean statusPrazoMaximo = false;
		if (curso.getPrazoMaximo() == null || curso.getPrazoMaximo() <= 0) {
			exceptionHelper.add("prazoMaximo inválido");
		} else {
			statusPrazoMaximo = true;
		}

		//Verifica prazoEsperado
		boolean statusPrazoEsperado = false;
		if (curso.getPrazoEsperado() == null || curso.getPrazoEsperado() <= 0) {
			exceptionHelper.add("prazoEsperado inválido");
		} else {
			statusPrazoEsperado = true;
		}

		//Verifica a relação entre prazoMinimo, prazoMaximo e prazoEsperado
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

		//Verifica disciplinasObrigatorias
		if (curso.getIdDisciplinasObrigatorias() != null) {
			for (Long idDisciplinaPeriodo : curso.getIdDisciplinasObrigatorias()) {
				if (idDisciplinaPeriodo == null || idDisciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.disciplinaPeriodoService.findById(idDisciplinaPeriodo);
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaObrigatoria(id=" + idDisciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica disciplinasOptativas
		if (curso.getIdDisciplinasOptativas() != null) {
			for (Long idDisciplina : curso.getIdDisciplinasOptativas()) {
				if (idDisciplina == null || idDisciplina < 0) {
					exceptionHelper.add("disciplinaOptativa inconsistente");
				} else {
					try {
						this.disciplinaService.findById(idDisciplina);
					} catch (EntityNotFoundException entityNotFoundException) {
						exceptionHelper.add("disciplinaOptativa(id=" + idDisciplina + ") inexistente");
					}
				}
			}
		}

		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return curso;
		} else {
			throw new ValidationException(exceptionHelper.getMessage());
		}
	}

}
