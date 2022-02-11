package br.ufrn.imd.app3.modelo;

import javax.persistence.*;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;

@Entity
@Table(
		name = "disciplinaperiodo"
)
public class DisciplinaPeriodo extends DisciplinaCursada implements DisciplinaInterface {

	private Integer periodo;

	public DisciplinaPeriodo(DisciplinaBTI disciplinaBTI, Integer periodo) {
		this.disciplina = disciplinaBTI;
		this.periodo = periodo;
	}

	public DisciplinaPeriodo() {

	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

}
