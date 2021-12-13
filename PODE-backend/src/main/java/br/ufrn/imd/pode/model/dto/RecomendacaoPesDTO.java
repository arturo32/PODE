package br.ufrn.imd.pode.model.dto;

import java.util.HashSet;
import java.util.Set;

import br.ufrn.imd.pode.model.view.DisciplinaPendente;

public class RecomendacaoPesDTO {

	private String nome;
	private Integer cargaHorariaObrigatoria;
	private Integer cargaHorariaObrigatoriaPendente;
	private Integer cargaHorariaOptativaMinima;
	private Integer cargaHorariaOptativaPendente;
	private Set<DisciplinaPendente> disciplinasObrigatorias;
	private Set<DisciplinaPendente> disciplinasOptativas;

	public RecomendacaoPesDTO() {
		this.nome = "";
		this.cargaHorariaObrigatoria = 0;
		this.cargaHorariaObrigatoriaPendente = 0;
		this.cargaHorariaOptativaMinima = 0;
		this.cargaHorariaOptativaPendente = 0;
		this.disciplinasObrigatorias = new HashSet<DisciplinaPendente>();
		this.disciplinasOptativas = new HashSet<DisciplinaPendente>();
	}

	public RecomendacaoPesDTO(String nome, Integer cargaHorariaObrigatoria,
			Integer cargaHorariaObrigatoriaPendente, Integer cargaHorariaOptativaMinima,
			Integer cargaHorariaOptativaPendente, Set<DisciplinaPendente> disciplinasObrigatorias,
			Set<DisciplinaPendente> disciplinasOptativas) {
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

	public Set<DisciplinaPendente> getDisciplinasObrigatorias() {
		return disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaPendente> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<DisciplinaPendente> getDisciplinasOptativas() {
		return disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<DisciplinaPendente> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}

}
