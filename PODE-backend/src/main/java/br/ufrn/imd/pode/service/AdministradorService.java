package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Administrador;
import br.ufrn.imd.pode.repository.AdministradorRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdministradorService extends GenericService<Administrador, Long> {

	private AdministradorRepository repository;


	@Override
	protected GenericRepository<Administrador, Long> repository() {
		return this.repository;
	}

	public AdministradorRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(AdministradorRepository repository) {
		this.repository = repository;
	}
}
