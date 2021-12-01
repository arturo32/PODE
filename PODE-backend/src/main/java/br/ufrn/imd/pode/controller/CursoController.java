package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.dto.CursoDTO;
import br.ufrn.imd.pode.service.CursoService;
import br.ufrn.imd.pode.service.GenericService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "curso")
public class CursoController extends GenericController<Curso, CursoDTO, Long> {

	private CursoService service;

	@Autowired
	public void setService(CursoService cursoService) {
		this.service = cursoService;
	}

	@Override
	protected GenericService<Curso, CursoDTO, Long> service() {
		return this.service;
	}

	@Override
	public ResponseEntity<CursoDTO> save(@Valid @RequestBody Curso curso) {
		return ResponseEntity.ok(this.service().convertToDto(this.service.salvar(curso)));
	}

}
