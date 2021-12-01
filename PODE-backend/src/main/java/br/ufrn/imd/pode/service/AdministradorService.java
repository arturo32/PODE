package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.model.Administrador;
import br.ufrn.imd.pode.model.dto.AdministradorDTO;
import br.ufrn.imd.pode.repository.AdministradorRepository;
import br.ufrn.imd.pode.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdministradorService extends GenericService<Administrador, AdministradorDTO, Long> {

	private AdministradorRepository repository;

	@Override
	public AdministradorDTO convertToDto(Administrador administrador) {
		// TODO
		return null;
	}

	@Override
	public Administrador convertToEntity(AdministradorDTO administradorDTO) {
		// TODO
		return null;
	}

	@Override
	protected GenericRepository<Administrador, Long> repository() {
		return this.repository;
	}

	public AdministradorRepository getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(AdministradorRepository administradorRepository) {
		this.repository = administradorRepository;
	}

}
