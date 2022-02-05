package br.ufrn.imd.app1.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufrn.imd.pode.controle.PlanoCursoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;

import br.ufrn.imd.app1.modelo.PlanoCursoPes;
import br.ufrn.imd.app1.modelo.dto.PlanoCursoPesDTO;
import br.ufrn.imd.app1.servico.PlanoCursoPesServico;

import java.util.List;

@RestController
@RequestMapping("/planos-de-curso")
public class PlanoCursoPesControlador extends PlanoCursoControlador<PlanoCursoPes, PlanoCursoPesDTO> {

	private PlanoCursoPesServico planoCursoPesServico;

	@Autowired
	public void setPlanoCursoPesServico(PlanoCursoPesServico planoCursoPesServico) {
		this.planoCursoPesServico = planoCursoPesServico;
	}

	@Override
	protected GenericoServico<PlanoCursoPes, PlanoCursoPesDTO, Long> servico() {
		return this.planoCursoPesServico;
	}

	@PostMapping({"/{planoCursoId}/disciplinas-cursadas"})
	public ResponseEntity<PlanoCursoPesDTO> adicionarDisciplinaCursada(@PathVariable Long planoCursoId, @RequestBody List<DisciplinaCursadaDTO> disciplinas) {
		return ResponseEntity.ok(this.planoCursoPesServico.converterParaDTO(this.planoCursoPesServico.
				adicionarDisciplinaCursada(planoCursoId, disciplinas)));
	}

	@PostMapping({"/{id}/remove-disciplinas-cursadas"})
	public ResponseEntity<PlanoCursoPesDTO> removerDisciplinaCursada(@PathVariable Long id, @RequestBody List<DisciplinaCursadaDTO> disciplinasDTOS) {
		return ResponseEntity.ok(this.planoCursoPesServico.converterParaDTO(this.planoCursoPesServico.
				removerDisciplinaCursada(id, disciplinasDTOS)));
	}

	@PostMapping({"/{id}/disciplinas-pendentes"})
	public ResponseEntity<PlanoCursoPesDTO> adicionarDisciplinaPendente(@PathVariable Long id, @RequestBody List<DisciplinaDTO> disciplinasDTOS) {
		return ResponseEntity.ok(this.planoCursoPesServico.converterParaDTO(this.planoCursoPesServico.
				adicionarDisciplinaPendente(id, disciplinasDTOS)));
	}

	@PostMapping({"/{id}/remove-disciplinas-pendentes"})
	public ResponseEntity<PlanoCursoPesDTO> removerDisciplinaPendente(@PathVariable Long id, @RequestBody List<DisciplinaDTO> disciplinasDTOS) {
		return ResponseEntity.ok(this.planoCursoPesServico.converterParaDTO(this.planoCursoPesServico.
				removerDisciplinaPendente(id, disciplinasDTOS)));
	}

}
