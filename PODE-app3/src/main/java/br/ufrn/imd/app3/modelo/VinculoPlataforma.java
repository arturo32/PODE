package br.ufrn.imd.app3.modelo;

import javax.persistence.*;
import java.util.Set;

import br.ufrn.imd.pode.modelo.Vinculo;

@Entity
@Table(name = "vinculoplataforma")
public class VinculoPlataforma extends Vinculo {

	@ManyToMany(
			cascade = {CascadeType.ALL}
	)
	@JoinTable(
			name = "vinculo_tema",
			joinColumns = {@JoinColumn(
					name = "vinculo_id"
			)},
			inverseJoinColumns = {@JoinColumn(
					name = "tema_id"
			)}
	)
	private Set<Tema> temasInteresse;

	public Set<Tema> getTemasInteresse() {
		return temasInteresse;
	}

	public void setTemasInteresse(Set<Tema> temasInteresse) {
		this.temasInteresse = temasInteresse;
	}
}
