package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.GradeCurricular;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
		name = "pes"
)
public class Pes extends GradeCurricular {
	private Integer chm;

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

}
