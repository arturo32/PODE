package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.PlanoCurso;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(
		name = "planocursopes"
)
public class PlanoCursoPes extends PlanoCurso {
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "planocursopes_interesse_pes", joinColumns = {
			@JoinColumn(name = "planocursopes_id") }, inverseJoinColumns = { @JoinColumn(name = "pes_id") })
	private List<Pes> gradesParalelas;

	public List<Pes> getGradesParalelas() {
		return gradesParalelas;
	}

	public void setGradesParalelas(List<Pes> gradesParalelas) {
		this.gradesParalelas = gradesParalelas;
	}

}
