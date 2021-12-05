package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.enums.Prioridade;

public class RecomendacaoDTO {

	private DisciplinaPeriodoDTO disciplinaPeriodo;
	private Prioridade prioridade;

	public RecomendacaoDTO() {
	}

	public RecomendacaoDTO(DisciplinaPeriodoDTO disciplinaPeriodo, Prioridade prioridade) {
		this.disciplinaPeriodo = disciplinaPeriodo;
		this.prioridade = prioridade;
	}

	public DisciplinaPeriodoDTO getDisciplinaPeriodo() {
		return disciplinaPeriodo;
	}

	public void setDisciplinaPeriodo(DisciplinaPeriodoDTO disciplinaPeriodo) {
		this.disciplinaPeriodo = disciplinaPeriodo;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

}
