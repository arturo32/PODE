package br.ufrn.imd.pode.controller;

import java.util.List;

import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.model.dto.RecomendacaoPesDTO;
import br.ufrn.imd.pode.service.RecomendacaoService;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

	private RecomendacaoService service;

	public RecomendacaoService getService() {
		return service;
	}

	@Autowired
	public void setService(RecomendacaoService service) {
		this.service = service;
	}

	@GetMapping("/disciplinas-por-proximadade-conclusao-pes/{id_vinculo}")
	public RecomendacaoPesDTO obterDisciplinasPorProximidadeConclusaoPes(
			@PathVariable(value = "id_vinculo") long vinculoId) {
		return this.service.recomendarDisciplinasPorProximidadeConclusaoPes(this.service.validate(vinculoId));
	}

	@GetMapping("/disciplinas-por-plano-de-curso/{id_vinculo}")
	public List<DisciplinaPeriodoDTO> recomendarDisciplinasPorPlanoDeCurso(
			@PathVariable(value = "id_vinculo") Long id_vinculo) {
		return this.service.recomendarDisciplinasPorPlanoDeCurso(this.service.validate(id_vinculo));
	}

}