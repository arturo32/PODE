package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.repository.CursoRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CursoService extends GenericService<Curso, Long> {

	CursoRepository repository;


	@Override
	protected GenericRepository<Curso, Long> repository() {
		return null;
	}

	public CursoRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(CursoRepository repository) {
		this.repository = repository;
	}
}
