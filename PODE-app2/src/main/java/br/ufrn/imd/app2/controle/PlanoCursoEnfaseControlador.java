package br.ufrn.imd.app2.controle;

import br.ufrn.imd.app2.modelo.dto.DisciplinaPeriodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufrn.imd.pode.controle.PlanoCursoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app2.modelo.PlanoCursoEnfase;
import br.ufrn.imd.app2.modelo.dto.PlanoCursoEnfaseDTO;
import br.ufrn.imd.app2.servico.PlanoCursoEnfaseServico;

@RestController
@RequestMapping("/planos-de-curso")
public class PlanoCursoEnfaseControlador extends PlanoCursoControlador<PlanoCursoEnfase, PlanoCursoEnfaseDTO, DisciplinaPeriodoDTO> {

	private PlanoCursoEnfaseServico planoCursoPesServico;

	@Autowired
	public void setPlanoCursoPesServico(PlanoCursoEnfaseServico planoCursoPesServico) {
		this.planoCursoPesServico = planoCursoPesServico;
	}

	@Override
	protected GenericoServico<PlanoCursoEnfase, PlanoCursoEnfaseDTO, Long> servico() {
		return this.planoCursoPesServico;
	}


	@PostMapping("/{id}/atualiza-enfase/{enfase}")
	public ResponseEntity<PlanoCursoEnfaseDTO> atualizaEnfase(@PathVariable Long id, @PathVariable Long enfase) {
		return ResponseEntity.ok(planoCursoPesServico.converterParaDTO(planoCursoPesServico.atualizaEnfase(id, enfase)));
	}

	@PostMapping("/{id}/remove-enfase")
	public ResponseEntity<PlanoCursoEnfaseDTO> removeInteressePes(@PathVariable Long id) {
		return ResponseEntity.ok(planoCursoPesServico.converterParaDTO(planoCursoPesServico.removeEnfase(id)));
	}
}
