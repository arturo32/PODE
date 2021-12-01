package br.ufrn.imd.pode.model.dto;

import java.util.Set;

public class CursoDTO extends AbstractDTO {

	private String nome;

	// carga horaria minima
	private Integer chm;

	// carga horaria obrigatoria
	private Integer cho;

	// carga horaria optativa minima
	private Integer chom;

	// carga horaria complementar minima
	private Integer chcm;

	// carga horaria eletiva maxima
	private Integer chem;

	// Carga horária mínima por período
	private Integer chminp;

	// Carga horária máxima por período
	private Integer chmaxp;

	private Integer prazoMinimo;

	private Integer prazoMaximo;

	private Integer prazoEsperado;

	private Set<DisciplinaPeriodoDTO> disciplinasObrigatorias;

	private Set<DisciplinaDTO> disciplinasOptativas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getChm() {
		return chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Integer getCho() {
		return cho;
	}

	public void setCho(Integer cho) {
		this.cho = cho;
	}

	public Integer getChom() {
		return chom;
	}

	public void setChom(Integer chom) {
		this.chom = chom;
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

	public Set<DisciplinaPeriodoDTO> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaPeriodoDTO> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<DisciplinaDTO> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<DisciplinaDTO> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}
}
