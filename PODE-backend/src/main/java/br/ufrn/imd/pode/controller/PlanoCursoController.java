package br.ufrn.imd.pode.controller;

import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.model.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.service.GenericService;
import br.ufrn.imd.pode.service.PlanoCursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/planos-de-curso")
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

	@PostMapping("/{id}/disciplinas-cursadas")
	public ResponseEntity<PlanoCursoDTO> adicionaDisciplinaCursada(@PathVariable Long id,
			@RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(service.convertToDto(service.adicionaDisciplinaCursada(id, disciplinasPeriodoDTOS)));
	}

	@PostMapping("/{id}/remove-disciplinas-cursadas")
	public ResponseEntity<PlanoCursoDTO> removeDisciplinaCursada(@PathVariable Long id,
			@RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(service.convertToDto(service.removeDisciplinaCursada(id, disciplinasPeriodoDTOS)));
	}

	@PostMapping("/{id}/disciplinas-pendentes")
	public ResponseEntity<PlanoCursoDTO> adicionaDisciplinaPendente(@PathVariable Long id,
			@RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(service.convertToDto(service.adicionaDisciplinaPendente(id, disciplinasPeriodoDTOS)));
	}

	@PostMapping("/{id}/remove-disciplinas-pendentes")
	public ResponseEntity<PlanoCursoDTO> removeDisciplinaPendente(@PathVariable Long id,
			@RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(service.convertToDto(service.removeDisciplinaPendente(id, disciplinasPeriodoDTOS)));
	}

	@PostMapping("/{id}/pes")
	public ResponseEntity<PlanoCursoDTO> adicionaInteressePes(@PathVariable Long id, @RequestBody List<Long> pesIds) {
		return ResponseEntity.ok(service.convertToDto(service.adicionaInteressePes(id, pesIds)));
	}

	@PostMapping("/{id}/remove-pes")
	public ResponseEntity<PlanoCursoDTO> removeInteressePes(@PathVariable Long id, @RequestBody List<Long> pesIds) {
		return ResponseEntity.ok(service.convertToDto(service.removeInteressePes(id, pesIds)));
	}
}
