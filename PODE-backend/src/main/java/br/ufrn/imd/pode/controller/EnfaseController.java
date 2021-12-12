package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.dto.EnfaseDTO;
import br.ufrn.imd.pode.service.EnfaseService;
import br.ufrn.imd.pode.service.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/enfase")
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

	@GetMapping("/curso/{cursoId}")
	public ResponseEntity<Collection<EnfaseDTO>> enfasesPorCurso(@PathVariable Long cursoId) {
		return ResponseEntity.ok(service.convertToDTOList(service.findEnfasePorCurso(cursoId)));
	}

}
