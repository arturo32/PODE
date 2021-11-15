package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Estudante;
import br.ufrn.imd.pode.repository.EstudanteRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EstudanteService extends GenericService<Estudante, Long>{

	private EstudanteRepository repository;


	@Override
	protected GenericRepository<Estudante, Long> repository() {
		return this.repository;
	}

	public EstudanteRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(EstudanteRepository repository) {
		this.repository = repository;
	}
}
