package br.ufrn.imd.pode.controle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import br.ufrn.imd.pode.modelo.PlanoCurso;
import br.ufrn.imd.pode.modelo.dto.DisciplinaCursadaDTO;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;
import br.ufrn.imd.pode.servico.PlanoCursoServico;

@RestController
@RequestMapping("/planos-de-curso")
public abstract class PlanoCursoControlador<T extends PlanoCurso, E extends PlanoCursoDTO, Disc extends DisciplinaCursadaDTO> extends GenericoControlador<T, E, Long> {

	private PlanoCursoServico<T, E, Disc> getPlanoCursoServico() {
		return (PlanoCursoServico<T, E, Disc>) this.servico();
	}

	@PostMapping("/{planoCursoId}/disciplinas-cursadas")
	public ResponseEntity<E> adicionarDisciplinaCursada(@PathVariable Long planoCursoId,
	                                                                @RequestBody List<Disc> disciplinas) {
		return ResponseEntity.ok(getPlanoCursoServico().converterParaDTO(getPlanoCursoServico().adicionarDisciplinaCursada(planoCursoId, disciplinas)));
	}

	@PostMapping("/{id}/remove-disciplinas-cursadas")
	public ResponseEntity<E> removerDisciplinaCursada(@PathVariable Long id,
	                                                              @RequestBody List<Disc> disciplinasDTOS) {
		return ResponseEntity.ok(getPlanoCursoServico().converterParaDTO(getPlanoCursoServico().removerDisciplinaCursada(id, disciplinasDTOS)));
	}

	@PostMapping("/{id}/disciplinas-pendentes")
	public ResponseEntity<E> adicionarDisciplinaPendente(@PathVariable Long id,
	                                                                 @RequestBody List<Disc> disciplinasDTOS) {
		return ResponseEntity.ok(getPlanoCursoServico().converterParaDTO(getPlanoCursoServico().adicionarDisciplinaPendente(id, disciplinasDTOS)));
	}

	@PostMapping("/{id}/remove-disciplinas-pendentes")
	public ResponseEntity<E> removerDisciplinaPendente(@PathVariable Long id,
	                                                               @RequestBody List<Disc> disciplinasDTOS) {
		return ResponseEntity.ok(getPlanoCursoServico().converterParaDTO(getPlanoCursoServico().removerDisciplinaPendente(id, disciplinasDTOS)));
	}

}
