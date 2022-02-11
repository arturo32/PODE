package br.ufrn.imd.app3.modelo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufrn.imd.pode.modelo.dto.PlanoCursoDTO;

import br.ufrn.imd.app3.modelo.PlanoCursoEnfase;

public class PlanoCursoEnfaseDTO extends PlanoCursoDTO {

	@JsonProperty("id-enfase")
	private Long idEnfase;

	public PlanoCursoEnfaseDTO() {
	}

	public PlanoCursoEnfaseDTO(PlanoCursoEnfase planoCurso) {
		super(planoCurso);
		idEnfase = planoCurso.getGradeSequencial().getId();
	}

	public Long getIdEnfase() {
		return idEnfase;
	}

	public void setIdEnfase(Long idEnfase) {
		this.idEnfase = idEnfase;
	}
}
