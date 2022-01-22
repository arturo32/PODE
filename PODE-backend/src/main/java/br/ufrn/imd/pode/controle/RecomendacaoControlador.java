package br.ufrn.imd.pode.controle;

import java.util.List;

import br.ufrn.imd.pode.modelo.dto.DisciplinaPeriodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.modelo.dto.RecomendacaoPesDTO;
import br.ufrn.imd.pode.servico.RecomendacaoServico;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoControlador {

	private RecomendacaoServico service;

	public RecomendacaoServico getService() {
		return service;
	}

	@Autowired
	public void setService(RecomendacaoServico service) {
		this.service = service;
	}

	@GetMapping("/disciplinas-por-proximadade-conclusao-pes/{id_vinculo}")
	public ResponseEntity<RecomendacaoPesDTO> obterDisciplinasPorProximidadeConclusaoPes(
			@PathVariable(value = "id_vinculo") long vinculoId) {
		return ResponseEntity.ok(this.service.recomendarDisciplinasPorProximidadeConclusaoPes(this.service.validar(vinculoId)));
	}

	@GetMapping("/disciplinas-por-plano-de-curso/{id_vinculo}")
	public ResponseEntity<List<DisciplinaPeriodoDTO>> recomendarDisciplinasPorPlanoDeCurso(
			@PathVariable(value = "id_vinculo") Long id_vinculo) {
		return ResponseEntity.ok(this.service.recomendarDisciplinasPorPlanoDeCurso(this.service.validar(id_vinculo)));
	}

}