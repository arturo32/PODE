package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.dto.EnfaseDTO;
import br.ufrn.imd.pode.service.EnfaseService;
import br.ufrn.imd.pode.service.GenericService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "enfase")
public class EnfaseController extends GenericController<Enfase, EnfaseDTO, Long> {

	private EnfaseService service;

	@Autowired
	public void setService(EnfaseService service) {
		this.service = service;
	}

	@Override
	protected GenericService<Enfase, EnfaseDTO, Long> service() {
		return this.service;
	}

	@Override
	public ResponseEntity<EnfaseDTO> save(@Valid @RequestBody Enfase enfase) {
		return ResponseEntity.ok(service().convertToDto(this.service.salvar(enfase)));
	}

}
