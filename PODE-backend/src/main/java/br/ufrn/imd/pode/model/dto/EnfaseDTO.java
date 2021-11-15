package br.ufrn.imd.pode.model.dto;


import java.util.Set;

public class EnfaseDTO {

	private Long id;

	private String nome;

	private CursoDTO curso;

	private Set<DisciplinaPeriodoDTO> disciplinasObrigatorias;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CursoDTO getCurso() {
		return curso;
	}

	public void setCurso(CursoDTO curso) {
		this.curso = curso;
	}

	public Set<DisciplinaPeriodoDTO> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaPeriodoDTO> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}
}
