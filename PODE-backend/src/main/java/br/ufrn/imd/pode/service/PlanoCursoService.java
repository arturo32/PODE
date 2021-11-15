package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.PlanoCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PlanoCursoService extends GenericService<PlanoCurso, Long> {

	private PlanoCursoRepository repository;


	@Override
	protected GenericRepository<PlanoCurso, Long> repository() {
		return this.repository;
	}

	public PlanoCursoRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(PlanoCursoRepository repository) {
		this.repository = repository;
	}
}
