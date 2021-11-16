package br.ufrn.imd.pode.service;

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
		return null;
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

	public void salvar(Disciplina disciplina){
		this.repository.save(disciplina);
	}
}
