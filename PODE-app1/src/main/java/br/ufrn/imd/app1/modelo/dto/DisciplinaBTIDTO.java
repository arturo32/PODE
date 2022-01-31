package br.ufrn.imd.app1.modelo.dto;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;

public class DisciplinaBTIDTO extends DisciplinaDTO {
	private String equivalentes;

	public DisciplinaBTIDTO() {
	}

	public DisciplinaBTIDTO(Disciplina entity) {
		super(entity);
	}

	public DisciplinaBTIDTO(DisciplinaBTI entity) {
		super(entity);
		this.setEquivalentes(entity.getEquivalentes());
	}

	public String getEquivalentes() {
		return equivalentes;
	}

	public void setEquivalentes(String equivalentes) {
		this.equivalentes = equivalentes;
	}
}
