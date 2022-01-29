package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.GradeCurricular;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
		name = "cursobti"
)
public class Pes extends GradeCurricular {
	private Integer chm;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "pes_disciplina_obrigatorias", joinColumns = {
			@JoinColumn(name = "pes_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	private Set<DisciplinaPeriodo> disciplinasObrigatorias;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "pes_disciplina_optativas", joinColumns = {
			@JoinColumn(name = "pes_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	private Set<DisciplinaBTI> disciplinasOptativas;

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Set<DisciplinaInterface> getDisciplinasObrigatorias() {
		return new HashSet<>(disciplinasObrigatorias);
	}

	public Set<DisciplinaInterface> getDisciplinasOptativas() {
		return new HashSet<>(disciplinasOptativas);
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaPeriodo> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public void setDisciplinasOptativas(Set<DisciplinaBTI> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}
}
