package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.Vinculo;

public class VinculoDTO extends AbstratoDTO {

	private String matricula;

	private Long idCurso;

	private Long idPlanoCurso;

	private Long idEstudante;

	public VinculoDTO(Vinculo vinculo) {
		this.setId(vinculo.getId());
		this.setMatricula(vinculo.getMatricula());
		this.setIdCurso(vinculo.getGradeCurricular().getId());
		this.setIdPlanoCurso(vinculo.getPlanoCurso().getId());
		this.setIdEstudante(vinculo.getEstudante().getId());
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public Long getIdPlanoCurso() {
		return idPlanoCurso;
	}

	public void setIdPlanoCurso(Long idPlanoCurso) {
		this.idPlanoCurso = idPlanoCurso;
	}

	public Long getIdEstudante() {
		return idEstudante;
	}

	public void setIdEstudante(Long idEstudante) {
		this.idEstudante = idEstudante;
	}
}
