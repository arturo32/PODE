package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Estudante;
import br.ufrn.imd.pode.model.dto.EstudanteDTO;
import br.ufrn.imd.pode.service.EstudanteService;
import br.ufrn.imd.pode.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "estudante")
public class EstudanteController extends GenericController<Estudante, EstudanteDTO, Long> {

	private EstudanteService service;

	@Autowired
	public void setService(EstudanteService service) {
		this.service = service;
	}

	@Override
	protected GenericService<Estudante, EstudanteDTO, Long> service() {
		return this.service;
	}

}
