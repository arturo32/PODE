package br.ufrn.imd.app3.modelo.dto;

import br.ufrn.imd.app3.modelo.Topico;
import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;

public class TopicoDTO extends AbstratoDTO {
	private String nome;
	private Long tema;

	public TopicoDTO(Topico topico) {
		this.setId(topico.getId());
		this.setNome(topico.getNome());
		this.setTema(topico.getTema().getId());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTema() {
		return tema;
	}

	public void setTema(Long tema) {
		this.tema = tema;
	}
}
