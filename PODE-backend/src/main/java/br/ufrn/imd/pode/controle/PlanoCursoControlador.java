package br.ufrn.imd.pode.controle;

import br.ufrn.imd.pode.modelo.PlanoCurso;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.servico.GenericoServico;
import br.ufrn.imd.pode.servico.PlanoCursoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos-de-curso")
public class PlanoCursoControlador extends GenericoControlador<PlanoCurso, PlanoCursoDTO, Long> {

	private PlanoCursoServico servico;

	@Autowired
	public void setServico(PlanoCursoServico servico) {
		this.servico = servico;
	}

	@Override
	protected GenericoServico<PlanoCurso, PlanoCursoDTO, Long> servico() {
		return this.servico;
	}

	@PostMapping("/{planoCursoId}/disciplinas-cursadas")
	public ResponseEntity<PlanoCursoDTO> adicionarDisciplinaCursada(@PathVariable Long planoCursoId,
																	@RequestBody List<DisciplinaDTO> disciplinas) {
		return ResponseEntity.ok(servico.converterParaDTO(servico.adicionarDisciplinaCursada(planoCursoId, disciplinas)));
	}

	@PostMapping("/{id}/remove-disciplinas-cursadas")
	public ResponseEntity<PlanoCursoDTO> removerDisciplinaCursada(@PathVariable Long id,
																  @RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(servico.converterParaDTO(servico.removerDisciplinaCursada(id, disciplinasPeriodoDTOS)));
	}

	@PostMapping("/{id}/disciplinas-pendentes")
	public ResponseEntity<PlanoCursoDTO> adicionarDisciplinaPendente(@PathVariable Long id,
																	 @RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(servico.converterParaDTO(servico.adicionarDisciplinaPendente(id, disciplinasPeriodoDTOS)));
	}

	@PostMapping("/{id}/remove-disciplinas-pendentes")
	public ResponseEntity<PlanoCursoDTO> removerDisciplinaPendente(@PathVariable Long id,
																   @RequestBody List<DisciplinaPeriodoDTO> disciplinasPeriodoDTOS) {
		return ResponseEntity.ok(servico.converterParaDTO(servico.removerDisciplinaPendente(id, disciplinasPeriodoDTOS)));
	}
}
