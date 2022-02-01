package br.ufrn.imd.app1.modelo;

import javax.persistence.*;
import java.util.Collection;

import br.ufrn.imd.pode.modelo.ModeloAbstrato;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;

@Entity
@Table(
		name = "disciplinaperiodo"
)
public class DisciplinaPeriodo extends DisciplinaCursada implements DisciplinaInterface {

	private Integer periodo;

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

}
