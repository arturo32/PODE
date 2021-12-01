package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.dto.PesDTO;
import br.ufrn.imd.pode.service.GenericService;
import br.ufrn.imd.pode.service.PesService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "pes")
public class PesController extends GenericController<Pes, PesDTO, Long> {

	private PesService service;

	@Autowired
	public void setService(PesService service) {
		this.service = service;
	}

	@Override
	protected GenericService<Pes, PesDTO, Long> service() {
		return this.service;
	}

	@Override
	public ResponseEntity<PesDTO> save(@Valid @RequestBody Pes pes) {
		return ResponseEntity.ok(service().convertToDto(this.service.salvar(pes)));
	}

}
