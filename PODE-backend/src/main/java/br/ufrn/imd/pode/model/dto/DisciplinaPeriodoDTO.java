package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.DisciplinaPeriodo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DisciplinaPeriodoDTO extends AbstractDTO {

	@JsonProperty("id-disciplina")
	private Long idDisciplina;

	private Integer periodo;

	public DisciplinaPeriodoDTO() {
	}

	public DisciplinaPeriodoDTO(DisciplinaPeriodo disciplinaPeriodo) {
		this.setId(disciplinaPeriodo.getId());
		this.setIdDisciplina(disciplinaPeriodo.getDisciplina().getId());
		this.setPeriodo(disciplinaPeriodo.getPeriodo());
	}

	public Long getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(Long idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
