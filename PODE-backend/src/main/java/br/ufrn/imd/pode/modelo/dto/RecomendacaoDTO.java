package br.ufrn.imd.pode.modelo.dto;

import java.util.Set;

public class RecomendacaoDTO {

	private String nome;
	private Integer cargaHorariaObrigatoria;
	private Integer cargaHorariaObrigatoriaPendente;
	private Integer cargaHorariaOptativaMinima;
	private Integer cargaHorariaOptativaPendente;
	private Set<Long> disciplinasObrigatorias;
	private Set<Long> disciplinasOptativas;

	public RecomendacaoDTO() {
		this.cargaHorariaObrigatoria = 0;
		this.cargaHorariaObrigatoriaPendente = 0;
		this.cargaHorariaOptativaMinima = 0;
		this.cargaHorariaOptativaPendente = 0;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCargaHorariaObrigatoria() {
		return cargaHorariaObrigatoria;
	}

	public void setCargaHorariaObrigatoria(Integer cargaHorariaObrigatoria) {
		this.cargaHorariaObrigatoria = cargaHorariaObrigatoria;
	}

	public Integer getCargaHorariaObrigatoriaPendente() {
		return cargaHorariaObrigatoriaPendente;
	}

	public void setCargaHorariaObrigatoriaPendente(Integer cargaHorariaObrigatoriaPendente) {
		this.cargaHorariaObrigatoriaPendente = cargaHorariaObrigatoriaPendente;
	}

	public Integer getCargaHorariaOptativaMinima() {
		return cargaHorariaOptativaMinima;
	}

	public void setCargaHorariaOptativaMinima(Integer cargaHorariaOptativaMinima) {
		this.cargaHorariaOptativaMinima = cargaHorariaOptativaMinima;
	}

	public Integer getCargaHorariaOptativaPendente() {
		return cargaHorariaOptativaPendente;
	}

	public void setCargaHorariaOptativaPendente(Integer cargaHorariaOptativaPendente) {
		this.cargaHorariaOptativaPendente = cargaHorariaOptativaPendente;
	}

	public Set<Long> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<Long> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<Long> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<Long> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}

}
