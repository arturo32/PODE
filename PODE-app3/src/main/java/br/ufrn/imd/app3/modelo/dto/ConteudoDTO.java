package br.ufrn.imd.app3.modelo.dto;

import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;

import br.ufrn.imd.app3.modelo.Conteudo;

public class ConteudoDTO extends DisciplinaDTO {

	Long tema;
	Long topico;
	String nivel;

	public ConteudoDTO() {
	}


	public ConteudoDTO(Conteudo entity) {
		super(entity);
		this.tema = entity.getTema().getId();
		this.topico = entity.getTopico().getId();
		this.nivel = entity.getNivel();
	}

	public Long getTema() {
		return tema;
	}

	public void setTema(Long tema) {
		this.tema = tema;
	}

	public Long getTopico() {
		return topico;
	}

	public void setTopico(Long topico) {
		this.topico = topico;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
}
