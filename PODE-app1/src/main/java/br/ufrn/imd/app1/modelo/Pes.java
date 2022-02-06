package br.ufrn.imd.app1.modelo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.GradeCurricular;

@Entity
@Table(name = "pes")
public class Pes extends GradeCurricular {
	// Carga horária
	private Integer chm;

	public Pes(String nome, Integer chm, Integer chobm, Integer chopm) {
		this.nome = nome;
		this.chm = chm;
		this.chobm = chobm;
		this.chopm = chopm;
	}

	public Pes() {

	}

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

}
