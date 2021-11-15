package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.repository.EnfaseRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EnfaseService extends GenericService<Enfase, Long> {

	private EnfaseRepository repository;


	@Override
	protected GenericRepository<Enfase, Long> repository() {
		return this.repository;
	}

	public EnfaseRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(EnfaseRepository repository) {
		this.repository = repository;
	}
}
