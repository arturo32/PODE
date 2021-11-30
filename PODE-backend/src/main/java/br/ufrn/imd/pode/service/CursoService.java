package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.ValidationException;
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

	public Curso salvar(Curso curso) {
		String excecao = "";
		if (curso.getCodigo() == null || curso.getCodigo().isEmpty()) {
			excecao += "Código inválido";
		}
		if (curso.getNome() == null || curso.getNome().isEmpty()) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "Nome inválido";
		}
		if (curso.getChm() == null || curso.getChm() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "chm inválido";
		}
		if (curso.getCho() == null || curso.getCho() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "cho inválido";
		}
		if (curso.getChom() == null || curso.getChom() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "chom inválido";
		}
		if (curso.getChcm() == null || curso.getChcm() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "chcm inválido";
		}
		if (curso.getChem() == null || curso.getChem() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "chem inválido";
		}
		if (curso.getChmp() == null || curso.getChmp() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "chmp inválido";
		}
		Boolean statusPrazoMinimo = false;
		if (curso.getPrazoMinimo() == null || curso.getPrazoMinimo() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "prazoMinimo inválido";
		} else {
			statusPrazoMinimo = true;
		}
		Boolean statusPrazoMaximo = false;
		if (curso.getPrazoMaximo() == null || curso.getPrazoMaximo() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "prazoMaximo inválido";
		} else {
			statusPrazoMaximo = true;
		}
		Boolean statusPrazoEsperado = false;
		if (curso.getPrazoEsperado() == null || curso.getPrazoEsperado() <= 0) {
			if (!excecao.isEmpty()) {
				excecao += ", ";
			}
			excecao += "prazoEsperado inválido";
		} else {
			statusPrazoEsperado = true;
		}
		if (statusPrazoMinimo && statusPrazoMaximo && statusPrazoEsperado) {
			if (curso.getPrazoMinimo() > curso.getPrazoMaximo()) {
				if (!excecao.isEmpty()) {
					excecao += ", ";
				}
				excecao += "impossível prazoMinimo ser maior do que prazoMaximo";
			}
			if (curso.getPrazoEsperado() < curso.getPrazoMinimo()) {
				if (!excecao.isEmpty()) {
					excecao += ", ";
				}
				excecao += "impossível prazoEsperado ser menor do que prazoMinimo";
			}
			if (curso.getPrazoEsperado() > curso.getPrazoMaximo()) {
				if (!excecao.isEmpty()) {
					excecao += ", ";
				}
				excecao += "impossível prazoEsperado ser maior do que prazoMaximo";
			}
		}
		if (curso.getDisciplinasObrigatorias() != null) {
			Iterator<DisciplinaPeriodo> iterador = curso.getDisciplinasObrigatorias().iterator();
			while (iterador.hasNext()) {
				DisciplinaPeriodo disciplinaPeriodo = iterador.next();
				if (disciplinaPeriodo.getId() == null || disciplinaPeriodo.getId() <= 0) {
					if (!excecao.isEmpty()) {
						excecao += ", ";
					}
					excecao += "disciplinasObrigatorias inválido";
					break;
				}
			}
		}
		if (curso.getDisciplinasOptativas() != null) {
			Iterator<Disciplina> iterador = curso.getDisciplinasOptativas().iterator();
			while (iterador.hasNext()) {
				Disciplina disciplina = iterador.next();
				if (disciplina.getId() == null || disciplina.getId() <= 0) {
					if (!excecao.isEmpty()) {
						excecao += ", ";
					}
					excecao += "disciplinasOptativas inválido";
					break;
				}
			}
		}
		if (excecao.isEmpty()) {
			return this.save(curso);
		} else {
			throw new ValidationException(excecao);
		}
	}

}
