package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.repository.DisciplinaRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DisciplinaService extends GenericService<Disciplina, Long> {

	private DisciplinaRepository repository;


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
}
