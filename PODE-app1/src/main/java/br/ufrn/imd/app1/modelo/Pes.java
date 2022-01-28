package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.GradeCurricular;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(
		name = "cursobti"
)
public class Pes extends GradeCurricular {
	private Integer chm;

	private Integer cho;

	private Set<DisciplinaBTI> disciplinasObrigatorias;

	private Set<DisciplinaBTI> disciplinasOptativas;

	@Override
	public Integer getChm() {
		return chm;
	}

	@Override
	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Integer getCho() {
		return cho;
	}

	public void setCho(Integer cho) {
		this.cho = cho;
	}

	public Set<DisciplinaBTI> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<DisciplinaBTI> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}
}
