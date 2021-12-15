package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.dto.DisciplinaDTO;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.PesDTO;
import br.ufrn.imd.pode.service.GenericService;
import br.ufrn.imd.pode.service.PesService;

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
@RequestMapping("/pes")
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

	@GetMapping("/{id}/disciplinas-obrigatorias")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasObrigatoriasPes(@PathVariable Long id) {
		Set<Disciplina> disciplinasObrigatorias = service.findById(id).getDisciplinasObrigatorias();
		return ResponseEntity
				.ok(disciplinasObrigatorias.stream().map(DisciplinaDTO::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}/disciplinas-optativas")
	public ResponseEntity<Collection<DisciplinaDTO>> buscarDisciplinasOptativasPes(@PathVariable Long id) {
		Set<Disciplina> disciplinasOptativas = service.findById(id).getDisciplinasOptativas();
		return ResponseEntity
				.ok(disciplinasOptativas.stream().map(DisciplinaDTO::new).collect(Collectors.toList()));
	}

}
