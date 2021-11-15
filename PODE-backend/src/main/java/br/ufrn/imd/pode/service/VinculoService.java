package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Vinculo;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.VinculoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class VinculoService extends GenericService<Vinculo, Long> {

	VinculoRepository repository;


	@Override
	protected GenericRepository<Vinculo, Long> repository() {
		return this.repository;
	}

	public VinculoRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(VinculoRepository repository) {
		this.repository = repository;
	}
}
