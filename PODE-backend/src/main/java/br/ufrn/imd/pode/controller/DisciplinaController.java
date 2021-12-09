package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.service.DisciplinaService;
import br.ufrn.imd.pode.service.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disciplina")
public class DisciplinaController extends GenericController<Disciplina, DisciplinaDTO, Long> {

	private DisciplinaService service;

	public DisciplinaService getDisciplinaService() {
		return this.service;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaService disciplinaService) {
		this.service = disciplinaService;
	}

	@Override
	protected GenericService<Disciplina, DisciplinaDTO, Long> service() {
		return this.service;
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinaCodigo(@PathVariable String codigo) {
		return ResponseEntity.ok(service.convertToDTOList(service.findDisciplinasByCodigo(codigo)));
	}

}
