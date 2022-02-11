package br.ufrn.imd.app3.modelo;

import javax.persistence.*;

import br.ufrn.imd.pode.modelo.PlanoCurso;

import java.util.Set;

@Entity
@Table(name = "planocursotema")
public class PlanoCursoTema extends PlanoCurso {
	@ManyToMany(
			cascade = {CascadeType.ALL}
	)
	@JoinTable(
			name = "planocurso_tema",
			joinColumns = {@JoinColumn(
					name = "planocurso_id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "tema_id"
			)}
	)
	private Set<Tema> temas;

	public Set<Tema> getTemas() {
		return temas;
	}

	public void setTemas(Set<Tema> temas) {
		this.temas = temas;
	}
}
