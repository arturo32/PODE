package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.Vinculo;

public class VinculoDTO extends AbstratoDTO {

	private String matricula;

	private Integer periodoInicialAno;

	private Integer periodoInicialPeriodo;

	private Integer periodoAtualAno;

	private Integer periodoAtualPeriodo;

	private Long idCurso;

	private Long idEnfase;

	private Long idPlanoCurso;

	private Long idEstudante;

	public VinculoDTO() {
	}

	public VinculoDTO(Vinculo vinculo) {
		this.setId(vinculo.getId());
		this.setMatricula(vinculo.getMatricula());
		this.setPeriodoInicialAno(vinculo.getPeriodoInicialAno());
		this.setPeriodoInicialPeriodo(vinculo.getPeriodoInicialPeriodo());
		this.setPeriodoAtualAno(vinculo.getPeriodoAtualAno());
		this.setPeriodoAtualPeriodo(vinculo.getPeriodoAtualPeriodo());
		this.setIdCurso(vinculo.getGradeCurricula().getId());
		if (vinculo.getEnfase() != null) {
			this.setIdEnfase(vinculo.getEnfase().getId());
		}
		this.setIdPlanoCurso(vinculo.getPlanoCurso().getId());
		this.setIdEstudante(vinculo.getEstudante().getId());
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Integer getPeriodoInicialAno() {
		return periodoInicialAno;
	}

	public void setPeriodoInicialAno(Integer periodoInicialAno) {
		this.periodoInicialAno = periodoInicialAno;
	}

	public Integer getPeriodoInicialPeriodo() {
		return periodoInicialPeriodo;
	}

	public void setPeriodoInicialPeriodo(Integer periodoInicialPeriodo) {
		this.periodoInicialPeriodo = periodoInicialPeriodo;
	}

	public Integer getPeriodoAtualAno() {
		return periodoAtualAno;
	}

	public void setPeriodoAtualAno(Integer periodoAtualAno) {
		this.periodoAtualAno = periodoAtualAno;
	}

	public Integer getPeriodoAtualPeriodo() {
		return periodoAtualPeriodo;
	}

	public void setPeriodoAtualPeriodo(Integer periodoAtualPeriodo) {
		this.periodoAtualPeriodo = periodoAtualPeriodo;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public Long getIdEnfase() {
		return idEnfase;
	}

	public void setIdEnfase(Long idEnfase) {
		this.idEnfase = idEnfase;
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
