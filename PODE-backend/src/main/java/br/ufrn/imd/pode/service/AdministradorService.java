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
		return new AdministradorDTO(administrador);
	}

	@Override
	public Administrador convertToEntity(AdministradorDTO dto) {
		Administrador administrador = new Administrador();

		//Se for uma edição
		if (dto.getId() != null){
			administrador = this.findById(administrador.getId());
		}

		administrador.setId(dto.getId());
		if (dto.getNome() != null) {
			administrador.setNome(dto.getNome());
		}
		if (dto.getEmail() != null) {
			administrador.setEmail(dto.getEmail());
		}
		if (dto.getSenha() != null) {
			administrador.setSenha(dto.getSenha());
		}

		return administrador;
	}

	@Override
	public AdministradorDTO validate(AdministradorDTO dto) {
		// TODO Validação
		return dto;
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
