package br.ufrn.imd.pode.modelo.dto;

import br.ufrn.imd.pode.modelo.Disciplina;

public class DisciplinaDTO extends AbstratoDTO {

	private String codigo;

	private String nome;

	private Integer ch;

	private String prerequisitos;

	public DisciplinaDTO() {
	}

	public DisciplinaDTO(Disciplina entity) {
		this.setId(entity.getId());
		this.setCodigo(entity.getCodigo());
		this.setNome(entity.getNome());
		this.setCh(entity.getCh());
		this.setPrerequisitos(entity.getPrerequisitos());
	}

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

	public String getPrerequisitos() {
		return prerequisitos;
	}

	public void setPrerequisitos(String prerequisitos) {
		this.prerequisitos = prerequisitos;
	}
}
