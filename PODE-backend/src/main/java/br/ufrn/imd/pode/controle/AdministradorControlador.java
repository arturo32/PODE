package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.Administrador;
import br.ufrn.imd.pode.modelo.dto.AdministradorDTO;
import br.ufrn.imd.pode.servico.AdministradorServico;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administradores")
public class AdministradorControlador extends GenericoControlador<Administrador, AdministradorDTO, Long> {

	private AdministradorServico service;

	@Autowired
	public void setService(AdministradorServico administradorService) {
		this.service = administradorService;
	}

	@Override
	protected GenericoServico<Administrador, AdministradorDTO, Long> servico() {
		return this.service;
	}
}
