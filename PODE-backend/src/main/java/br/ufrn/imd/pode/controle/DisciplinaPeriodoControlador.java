package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.DisciplinaPeriodo;
import br.ufrn.imd.pode.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.servico.DisciplinaPeriodoServico;
import br.ufrn.imd.pode.servico.GenericoServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/disciplinas-periodo")
public class DisciplinaPeriodoControlador extends GenericControlador<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaPeriodoServico service;

	@Autowired
	public void setService(DisciplinaPeriodoServico service) {
		this.service = service;
	}

	@Override
	protected GenericoServico<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> service() {
		return this.service;
	}

	@GetMapping("/planos-de-curso/{planoCursoId}/pendentes")
	public ResponseEntity<Collection<DisciplinaPeriodoDTO>> disciplinaPendentesPlanoCurso(Long planoCursoId) {
		return ResponseEntity.ok(service.convertToDTOList(service.disciplinaPendentesPlanoCurso(planoCursoId)));
	}

	@GetMapping("/planos-de-curso/{planoCursoId}/cursadas")
	public ResponseEntity<Collection<DisciplinaPeriodoDTO>> disciplinaCursadasPlanoCurso(Long planoCursoId) {
		return ResponseEntity.ok(service.convertToDTOList(service.disciplinaCursadasPlanoCurso(planoCursoId)));
	}
}
