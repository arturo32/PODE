package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enfases")
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

	@GetMapping("/cursos/{cursoId}")
	public ResponseEntity<Collection<EnfaseDTO>> enfasesPorCurso(@PathVariable Long cursoId) {
		return ResponseEntity.ok(service.convertToDTOList(service.findEnfasePorCurso(cursoId)));
	}

	@GetMapping("/{id}/disciplinas-obrigatorias")
	public ResponseEntity<Collection<DisciplinaPeriodoDTO>> buscarDisciplinasObrigatoriasEnfase(@PathVariable Long id) {
		Set<DisciplinaPeriodo> disciplinasObrigatorias = service.findById(id).getDisciplinasObrigatorias();
		return ResponseEntity
				.ok(disciplinasObrigatorias.stream().map(DisciplinaPeriodoDTO::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}/disciplinas-optativas")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasOptativasEnfase(@PathVariable Long id) {
		Set<Disciplina> disciplinasOptativas = service.findById(id).getDisciplinasOptativas();
		return ResponseEntity
				.ok(disciplinasOptativas.stream().map(DisciplinaDTO::new).collect(Collectors.toList()));
	}

}
