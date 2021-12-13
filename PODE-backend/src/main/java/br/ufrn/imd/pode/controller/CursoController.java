package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.dto.CursoDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.service.CursoService;
import br.ufrn.imd.pode.service.GenericService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/curso")
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

	@GetMapping("/{id}/disciplinas_obrigatorias")
	public ResponseEntity<Collection<DisciplinaPeriodoDTO>> buscarDisciplinasObrigatoriasCurso(@PathVariable Long id) {
		Set<DisciplinaPeriodo> disciplinasObrigatorias = service.findById(id).getDisciplinasObrigatorias();
		return ResponseEntity.ok(disciplinasObrigatorias.stream().map(DisciplinaPeriodoDTO::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}/disciplinas_optativas")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasOptativasCurso(@PathVariable Long id) {
		Set<Disciplina> disciplinasOptativas = service.findById(id).getDisciplinasOptativas();
		return ResponseEntity.ok(disciplinasOptativas.stream().map(DisciplinaDTO::new).collect(Collectors.toList()));
	}

}
