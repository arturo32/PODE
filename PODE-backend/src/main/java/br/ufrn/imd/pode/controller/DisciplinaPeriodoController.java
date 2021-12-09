package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.service.DisciplinaPeriodoService;
import br.ufrn.imd.pode.service.GenericService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/disciplina_periodo")
public class DisciplinaPeriodoController extends GenericController<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaPeriodoService service;

	@Autowired
	public void setService(DisciplinaPeriodoService service) {
		this.service = service;
	}

	@Override
	protected GenericService<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> service() {
		return this.service;
	}

	@GetMapping("/planocurso/{planoCursoId}/pendentes")
	public ResponseEntity<Collection<DisciplinaPeriodoDTO>> disciplinaPendentesPlanoCurso(Long planoCursoId) {
		return ResponseEntity.ok(service.convertToDTOList(service.disciplinaPendentesPlanoCurso(planoCursoId)));
	}

	@GetMapping("/planocurso/{planoCursoId}/cursadas")
	public ResponseEntity<Collection<DisciplinaPeriodoDTO>> disciplinaCursadasPlanoCurso(Long planoCursoId) {
		return ResponseEntity.ok(service.convertToDTOList(service.disciplinaCursadasPlanoCurso(planoCursoId)));
	}
}
