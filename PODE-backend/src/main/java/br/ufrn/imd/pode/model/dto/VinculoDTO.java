package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Vinculo;

public class VinculoDTO extends AbstractDTO {

	private String matricula;

	private Integer periodoInicial;

	private Integer periodoAtual;

	private Long curso;

	private Long enfase;

	private Long planoCurso;

	private Long estudante;

	public VinculoDTO() {
	}

	public VinculoDTO(Vinculo vinculo) {
		this.setId(vinculo.getId());
		this.setMatricula(vinculo.getMatricula());
		this.setPeriodoInicial(vinculo.getPeriodoInicial());
		this.setPeriodoAtual(vinculo.getPeriodoAtual());
		this.setCurso(vinculo.getCurso().getId());
		if(vinculo.getEnfase() != null){
			this.setEnfase(vinculo.getEnfase().getId());
		}
		this.setPlanoCurso(vinculo.getPlanoCurso().getId());
		this.setEstudante(vinculo.getEstudante().getId());
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

	public Long getCurso() {
		return curso;
	}

	public void setCurso(Long curso) {
		this.curso = curso;
	}

	public Long getEnfase() {
		return enfase;
	}

	public void setEnfase(Long enfase) {
		this.enfase = enfase;
	}

	public Long getPlanoCurso() {
		return planoCurso;
	}

	public void setPlanoCurso(Long planoCurso) {
		this.planoCurso = planoCurso;
	}

	public Long getEstudante() {
		return estudante;
	}

	public void setEstudante(Long estudante) {
		this.estudante = estudante;
	}
}
