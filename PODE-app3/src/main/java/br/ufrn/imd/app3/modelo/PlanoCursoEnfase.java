package br.ufrn.imd.app3.modelo;

import javax.persistence.*;

import br.ufrn.imd.pode.modelo.PlanoCurso;

@Entity
@Table(name = "planocursoenfase")
public class PlanoCursoEnfase extends PlanoCurso {
	@ManyToOne
	private Enfase gradeSequencial;

	public Enfase getGradeSequencial() {
		return gradeSequencial;
	}

	public void setGradeSequencial(Enfase gradeSequencial) {
		this.gradeSequencial = gradeSequencial;
	}
}
