package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Administrador;
import br.ufrn.imd.pode.model.dto.AdministradorDTO;
import br.ufrn.imd.pode.model.dto.EstudanteDTO;
import br.ufrn.imd.pode.service.AdministradorService;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/administrador")
public class AdministradorController extends GenericController<Administrador, AdministradorDTO, Long> {

	private AdministradorService service;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	public void setService(AdministradorService administradorService) {
		this.service = administradorService;
	}

	@Override
	protected GenericService<Administrador, AdministradorDTO, Long> service() {
		return this.service;
	}

	@Override
	public ResponseEntity<AdministradorDTO> save(@RequestBody AdministradorDTO dto) {
		dto.setSenha(encoder.encode(dto.getSenha()));
		return super.save(dto);
	}
}
