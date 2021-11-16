package br.ufrn.imd.pode.model.dto;

import java.util.HashSet;
import java.util.Set;

public class DisciplinaDTO extends AbstractDTO {

	private String codigo;

	private String nome;

	private Integer ch;

	private Set<DisciplinaDTO> prerequisitos;

	private Set<DisciplinaDTO> corequisitos;

	private String equivalentes;

	public DisciplinaDTO() {
		this.prerequisitos = new HashSet<>();
		this.corequisitos = new HashSet<>();
		this.equivalentes = new HashSet<>();
	}

	/*public DisciplinaDTO(Disciplina novaDisciplina) {
		this.id = novaDisciplina.getId();
		this.codigo = novaDisciplina.getCodigo();
		this.nome = novaDisciplina.getNome();
		this.ch = novaDisciplina.getCh();
	}*/

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCh() {
		return ch;
	}

	public void setCh(Integer ch) {
		this.ch = ch;
	}

	public Set<DisciplinaDTO> getPrerequisitos() {
		return prerequisitos;
	}

	public void setPrerequisitos(Set<DisciplinaDTO> prerequisitos) {
		this.prerequisitos = prerequisitos;
	}

	public Set<DisciplinaDTO> getCorequisitos() {
		return corequisitos;
	}

	public void setCorequisitos(Set<DisciplinaDTO> corequisitos) {
		this.corequisitos = corequisitos;
	}

	public String getEquivalentes() {
		return equivalentes;
	}

	public void setEquivalentes(String equivalentes) {
		this.equivalentes = equivalentes;
	}
}
