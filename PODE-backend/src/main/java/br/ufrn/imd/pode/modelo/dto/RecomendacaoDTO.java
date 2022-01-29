package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.DisciplinaInterface;

import java.util.HashSet;
import java.util.Set;

public class RecomendacaoDTO {

	private String nome;
	private Integer cargaHorariaObrigatoria;
	private Integer cargaHorariaObrigatoriaPendente;
	private Integer cargaHorariaOptativaMinima;
	private Integer cargaHorariaOptativaPendente;
	private Set<DisciplinaInterface> disciplinasObrigatorias;
	private Set<DisciplinaInterface> disciplinasOptativas;

	public RecomendacaoDTO() {
		this.nome = "";
		this.cargaHorariaObrigatoria = 0;
		this.cargaHorariaObrigatoriaPendente = 0;
		this.cargaHorariaOptativaMinima = 0;
		this.cargaHorariaOptativaPendente = 0;
		this.disciplinasObrigatorias = new HashSet<>();
		this.disciplinasOptativas = new HashSet<>();
	}

	public RecomendacaoDTO(String nome, Integer cargaHorariaObrigatoria,
	                       Integer cargaHorariaObrigatoriaPendente, Integer cargaHorariaOptativaMinima,
	                       Integer cargaHorariaOptativaPendente, Set<DisciplinaInterface> disciplinasObrigatorias,
	                       Set<DisciplinaInterface> disciplinasOptativas) {
		this.nome = nome;
		this.cargaHorariaObrigatoria = cargaHorariaObrigatoria;
		this.cargaHorariaObrigatoriaPendente = cargaHorariaObrigatoriaPendente;
		this.cargaHorariaOptativaMinima = cargaHorariaOptativaMinima;
		this.cargaHorariaOptativaPendente = cargaHorariaOptativaPendente;
		this.disciplinasObrigatorias = disciplinasObrigatorias;
		this.disciplinasOptativas = disciplinasOptativas;
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

	public Set<DisciplinaInterface> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaInterface> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<DisciplinaInterface> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<DisciplinaInterface> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}

}
