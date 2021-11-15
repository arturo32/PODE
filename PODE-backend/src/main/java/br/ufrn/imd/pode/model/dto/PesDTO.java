package br.ufrn.imd.pode.model.dto;

import br.ufrn.imd.pode.model.Disciplina;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class PesDTO {
	private Long id;

	private String nome;

	// carga horaria minima
	private Integer chm;

	// carga horaria obrigatoria
	private Integer cho;

	private Set<DisciplinaDTO> disciplinasObrigatorias;

	private Set<DisciplinaDTO> disciplinasOptativas;

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

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Integer getCho() {
		return cho;
	}

	public void setCho(Integer cho) {
		this.cho = cho;
	}

	public Set<DisciplinaDTO> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaDTO> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<DisciplinaDTO> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<DisciplinaDTO> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}
}
