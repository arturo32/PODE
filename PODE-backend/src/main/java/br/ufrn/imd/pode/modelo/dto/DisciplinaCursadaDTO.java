package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;

public class DisciplinaCursadaDTO extends AbstratoDTO {

	Long disciplinaId;

	public DisciplinaCursadaDTO() {
	}

	public DisciplinaCursadaDTO(DisciplinaCursada disciplinaCursada) {
		setId(disciplinaCursada.getId());
		setDisciplinaId(disciplinaCursada.getDisciplina().getId());
	}

	public Long getDisciplinaId() {
		return disciplinaId;
	}

	public void setDisciplinaId(Long disciplinaId) {
		this.disciplinaId = disciplinaId;
	}
}
