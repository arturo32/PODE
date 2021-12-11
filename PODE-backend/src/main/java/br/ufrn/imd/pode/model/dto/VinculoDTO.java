package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Vinculo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VinculoDTO extends AbstractDTO {

	private String matricula;

	private Integer periodoInicial;

	private Integer periodoAtual;

	@JsonProperty("id-curso")
	private Long idCurso;

	@JsonProperty("id-enfase")
	private Long idEnfase;

	@JsonProperty("id-plano-curso")
	private Long idPlanoCurso;

	@JsonProperty("id-estudante")
	private Long idEstudante;

	public VinculoDTO() {
	}

	public VinculoDTO(Vinculo vinculo) {
		this.setId(vinculo.getId());
		this.setMatricula(vinculo.getMatricula());
		this.setPeriodoInicial(vinculo.getPeriodoInicial());
		this.setPeriodoAtual(vinculo.getPeriodoAtual());
		this.setIdCurso(vinculo.getCurso().getId());
		if(vinculo.getEnfase() != null){
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

	public Integer getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Integer periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Integer getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(Integer periodoAtual) {
		this.periodoAtual = periodoAtual;
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
