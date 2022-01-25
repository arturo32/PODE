package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.PlanoCurso;

import java.util.List;

public class PlanoCursoPes extends PlanoCurso {
	private List<Pes> gradesParalelas;

	public List<Pes> getGradesParalelas() {
		return gradesParalelas;
	}

	public void setGradesParalelas(List<Pes> gradesParalelas) {
		this.gradesParalelas = gradesParalelas;
	}
}
