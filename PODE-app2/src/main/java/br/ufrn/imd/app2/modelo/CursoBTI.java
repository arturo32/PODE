package br.ufrn.imd.app2.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.ufrn.imd.pode.modelo.GradeCurricular;

@Entity
@Table(name = "cursobti")
public class CursoBTI extends GradeCurricular {

	@NotNull
	// Carga horária mínima
	private Integer chm;

	@NotNull
	// Carga horária complementar mínima
	private Integer chcm;

	@NotNull
	// Carga horária eletiva máxima
	private Integer chem;

	@NotNull
	// Carga horária mínima por período
	private Integer chminp;

	@NotNull
	// Carga horária máxima por período
	private Integer chmaxp;

	private Integer prazoMinimo;

	private Integer prazoMaximo;

	private Integer prazoEsperado;

	public CursoBTI(String nome, Integer chm, Integer chobm, Integer chopm, Integer chcm, Integer chem, Integer chminp,
	             Integer chmaxp, Integer prazoMinimo, Integer prazoMaximo, Integer prazoEsperado) {
		this.nome = nome;
		this.chm = chm;
		this.chobm = chobm;
		this.chopm = chopm;
		this.chcm = chcm;
		this.chem = chem;
		this.chminp = chminp;
		this.chmaxp = chmaxp;
		this.prazoMinimo = prazoMinimo;
		this.prazoMaximo = prazoMaximo;
		this.prazoEsperado = prazoEsperado;
	}

	public CursoBTI() {

	}

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
