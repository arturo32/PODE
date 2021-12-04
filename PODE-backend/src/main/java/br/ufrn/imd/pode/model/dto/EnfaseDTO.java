package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Enfase;

import java.util.Set;

public class EnfaseDTO extends AbstractDTO {

	private String nome;

	private CursoDTO curso;

	private Set<DisciplinaPeriodoDTO> disciplinasObrigatorias;

	public EnfaseDTO() {
	}

	public EnfaseDTO(Enfase enfase) {
		setId(enfase.getId());
		setNome(enfase.getNome());
		setCurso(new CursoDTO(enfase.getCurso()));
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
