package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.repository.DisciplinaRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DisciplinaService extends GenericService<Disciplina, DisciplinaDTO, Long> {

	private DisciplinaRepository repository;


	@Override
	public DisciplinaDTO convertToDto(Disciplina entity) {
		return null;
	}

	@Override
	public Disciplina convertToEntity(DisciplinaDTO dto) {
		if (dto.getId() != null) {
			return this.findById(dto.getId());
		}
		Disciplina disciplina = new Disciplina();
		disciplina.setId(dto.getId());
		disciplina.setCodigo(dto.getCodigo());
		disciplina.setNome(dto.getNome());
		disciplina.setCh(dto.getCh());

		//todo Validar expresão de equivalência
		disciplina.setEquivalentes(dto.getEquivalentes());

		for (DisciplinaDTO prerequisito : dto.getPrerequisitos()) {
			if (prerequisito.getId() != null) {
				disciplina.getPrerequisitos().add(this.findById(prerequisito.getId()));
			} else {
				throw new EntityNotFoundException("Não é possível cadastrar uma disciplina nova como prerequisito de outra.");
			}
		}
		for (DisciplinaDTO corequisito : dto.getCorequisitos()) {
			if (corequisito.getId() != null) {
				disciplina.getCorequisitos().add(this.findById(corequisito.getId()));
			} else {
				throw new EntityNotFoundException("Não é possível cadastrar uma disciplina nova como corequisito de outra.");
			}
		}
		return disciplina;
	}

	@Override
	protected GenericRepository<Disciplina, Long> repository() {
		return this.repository;
	}

	public DisciplinaRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(DisciplinaRepository repository) {
		this.repository = repository;
	}

	public void salvar(Disciplina disciplina) {
		this.repository.save(disciplina);
	}
}
