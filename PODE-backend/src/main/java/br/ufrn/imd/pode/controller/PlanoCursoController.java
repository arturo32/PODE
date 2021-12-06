package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.service.GenericService;
import br.ufrn.imd.pode.service.PlanoCursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plano_curso")
public class PlanoCursoController extends GenericController<PlanoCurso, PlanoCursoDTO, Long> {

	private PlanoCursoService service;

	@Autowired
	public void setService(PlanoCursoService service) {
		this.service = service;
	}

	@Override
	protected GenericService<PlanoCurso, PlanoCursoDTO, Long> service() {
		return this.service;
	}


	@PostMapping("{id}/adicionaDisciplinaCursada")
	public ResponseEntity<PlanoCurso> adicionaDisciplinaCursada(@PathVariable Long id, @RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(service.adicionaDisciplinaCursada(id, disciplinasPeriodoDTOS));
	}

	@PostMapping("{id}/adicionaDisciplinaPendente")
	public ResponseEntity<PlanoCurso> adicionaDisciplinaPendente(@PathVariable Long id, @RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(service.adicionaDisciplinaPendente(id, disciplinasPeriodoDTOS));
	}
}
