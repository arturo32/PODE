package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Vinculo;
import br.ufrn.imd.pode.service.GenericService;
import br.ufrn.imd.pode.service.VinculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "vinculo")
public class VinculoController extends GenericController<Vinculo, Long> {

	private VinculoService service;

	@Autowired
	public void setService(VinculoService service) {
		this.service = service;
	}

	@Override
	protected GenericService<Vinculo, Long> service() {
		return this.service;
	}
}
