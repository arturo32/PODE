package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.DisciplinaPeriodo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisciplinaPeriodoDTO extends AbstratoDTO {

	@JsonProperty("id-disciplina")
	private Long idDisciplina;

	private String codigo;

	private String nome;

	private Integer ch;

	private Integer periodo;

	public DisciplinaPeriodoDTO() {
	}

	public DisciplinaPeriodoDTO(DisciplinaPeriodo disciplinaPeriodo) {
		this.setId(disciplinaPeriodo.getId());
		this.setIdDisciplina(disciplinaPeriodo.getDisciplina().getId());
		this.setCodigo(disciplinaPeriodo.getDisciplina().getCodigo());
		this.setNome(disciplinaPeriodo.getDisciplina().getNome());
		this.setCh(disciplinaPeriodo.getDisciplina().getCh());
		this.setPeriodo(disciplinaPeriodo.getPeriodo());
	}

	public Long getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(Long idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCh() {
		return ch;
	}

	public void setCh(Integer ch) {
		this.ch = ch;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
