package br.ufrn.imd.app3.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufrn.imd.pode.modelo.GradeCurricular;

import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso extends GradeCurricular {

	@NotNull
	// Carga horária mínima
	private Integer chm;

	@ManyToMany(
			cascade = {CascadeType.ALL}
	)
	@JoinTable(
			name = "curso_temas",
			joinColumns = {@JoinColumn(
					name = "curso_id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "tema_id"
			)}
	)
	private Set<Tema> temas;

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Set<Tema> getTemas() {
		return temas;
	}

	public void setTemas(Set<Tema> temas) {
		this.temas = temas;
	}
}
