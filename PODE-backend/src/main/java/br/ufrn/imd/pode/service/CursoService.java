package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.dto.CursoDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.repository.CursoRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CursoService extends GenericService<Curso, CursoDTO, Long> {

	CursoRepository repository;
	DisciplinaPeriodoService disciplinaPeriodoService;
	DisciplinaService disciplinaService;

	@Override
	public CursoDTO convertToDto(Curso entity) {
		return null;
	}

	@Override
	public Curso convertToEntity(CursoDTO dto) {
		Curso curso = new Curso();
		curso.setId(dto.getId());
		curso.setCodigo(dto.getCodigo());
		curso.setNome(dto.getNome());
		curso.setChm(dto.getChm());
		curso.setCho(dto.getCho());
		curso.setChom(dto.getChom());
		curso.setChcm(dto.getChcm());
		curso.setChem(dto.getChem());
		curso.setChmp(dto.getChmp());
		curso.setPrazoMinimo(dto.getPrazoMinimo());
		curso.setPrazoMaximo(dto.getPrazoMaximo());
		curso.setPrazoEsperado(dto.getPrazoEsperado());
		for (DisciplinaPeriodoDTO disciplinaPeriodoDTO : dto.getDisciplinasObrigatorias()) {
			if (disciplinaPeriodoDTO.getId() != null) {
				curso.getDisciplinasObrigatorias()
						.add(this.disciplinaPeriodoService.findById(disciplinaPeriodoDTO.getId()));
			} else {
				throw new EntityNotFoundException("Disciplina obrigatória inexistente");
			}
		}
		for (DisciplinaDTO disciplinaDTO : dto.getDisciplinasOptativas()) {
			if (disciplinaDTO.getId() != null) {
				curso.getDisciplinasOptativas().add(this.disciplinaService.findById(disciplinaDTO.getId()));
			} else {
				throw new EntityNotFoundException("Disciplina optativa inexistente");
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
	public void setRepository(CursoRepository repository) {
		this.repository = repository;
	}

	public DisciplinaPeriodoService getDisciplinaPeriodoService() {
		return disciplinaPeriodoService;
	}

	@Autowired
	public void setDisciplinaPeriodoService(DisciplinaPeriodoService disciplinaPeriodoService) {
		this.disciplinaPeriodoService = disciplinaPeriodoService;
	}

	public DisciplinaService getDisciplinaService() {
		return disciplinaService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	public void salvar(Curso curso) {
		this.repository.save(curso);
	}

}
