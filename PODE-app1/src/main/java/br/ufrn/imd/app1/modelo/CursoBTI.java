package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.GradeCurricular;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
		name = "cursobti"
)
public class CursoBTI extends GradeCurricular {

	private Integer chcm;

	private Integer chem;

	private Integer chminp;

	private Integer chmaxp;

	private Integer prazoMinimo;

	private Integer prazoMaximo;

	private Integer prazoEsperado;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "cursobti_disciplina_obrigatorias", joinColumns = {
	@JoinColumn(name = "cursobti_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	private Set<DisciplinaPeriodo> disciplinasObrigatorias;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "cursobti_disciplina_optativas", joinColumns = {
			@JoinColumn(name = "cursobti_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	private Set<DisciplinaBTI> disciplinasOptativas;

	public Integer getChcm() {
		return chcm;
	}

	public void setChcm(Integer chcm) {
		this.chcm = chcm;
	}

	public Integer getChem() {
		return chem;
	}

	public void setChem(Integer chem) {
		this.chem = chem;
	}

	public Integer getChminp() {
		return chminp;
	}

	public void setChminp(Integer chminp) {
		this.chminp = chminp;
	}

	public Integer getChmaxp() {
		return chmaxp;
	}

	public void setChmaxp(Integer chmaxp) {
		this.chmaxp = chmaxp;
	}

	public Integer getPrazoMinimo() {
		return prazoMinimo;
	}

	public void setPrazoMinimo(Integer prazoMinimo) {
		this.prazoMinimo = prazoMinimo;
	}

	public Integer getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(Integer prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	public Integer getPrazoEsperado() {
		return prazoEsperado;
	}

	public void setPrazoEsperado(Integer prazoEsperado) {
		this.prazoEsperado = prazoEsperado;
	}

	public Set<DisciplinaInterface> getDisciplinasObrigatorias() {
		return new HashSet<>(disciplinasObrigatorias);
	}

	public Set<DisciplinaInterface> getDisciplinasOptativas() {
		return new HashSet<>(disciplinasOptativas);
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaPeriodo> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public void setDisciplinasOptativas(Set<DisciplinaBTI> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}
}
