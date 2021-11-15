package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Curso;
import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.PlanoCurso;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class VinculoDTO {

	private Long id;

	private String matricula;

	private Integer periodoInicial;

	private Integer periodoAtual;

	private CursoDTO curso;

	private List<EnfaseDTO> enfase;

	private PlanoCursoDTO planoCurso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CursoDTO getCurso() {
		return curso;
	}

	public void setCurso(CursoDTO curso) {
		this.curso = curso;
	}

	public List<EnfaseDTO> getEnfase() {
		return enfase;
	}

	public void setEnfase(List<EnfaseDTO> enfase) {
		this.enfase = enfase;
	}

	public PlanoCursoDTO getPlanoCurso() {
		return planoCurso;
	}

	public void setPlanoCurso(PlanoCursoDTO planoCurso) {
		this.planoCurso = planoCurso;
	}
}
