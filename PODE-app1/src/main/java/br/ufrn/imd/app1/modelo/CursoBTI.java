package br.ufrn.imd.app1.modelo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.GradeCurricular;

@Entity
@Table(name = "cursobti")
public class CursoBTI extends GradeCurricular {

	private Integer chm;

	private Integer chcm;

	private Integer chem;

	private Integer chminp;

	private Integer chmaxp;

	private Integer prazoMinimo;

	private Integer prazoMaximo;

	private Integer prazoEsperado;

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

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
}
