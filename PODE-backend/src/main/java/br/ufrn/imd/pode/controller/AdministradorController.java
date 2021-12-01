package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Administrador;
import br.ufrn.imd.pode.model.dto.AdministradorDTO;
import br.ufrn.imd.pode.service.AdministradorService;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "administrador")
public class AdministradorController extends GenericController<Administrador, AdministradorDTO, Long> {

	private AdministradorService service;

	@Autowired
	public void setService(AdministradorService administradorService) {
		this.service = administradorService;
	}

	@Override
	protected GenericService<Administrador, AdministradorDTO, Long> service() {
		return this.service;
	}

}
