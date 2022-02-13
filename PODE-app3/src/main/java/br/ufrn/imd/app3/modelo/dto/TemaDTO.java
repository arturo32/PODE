package br.ufrn.imd.app3.modelo.dto;

import java.util.Set;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;

import br.ufrn.imd.app3.modelo.Tema;
import br.ufrn.imd.app3.modelo.Topico;

public class TemaDTO extends AbstratoDTO {
	private String nome;
	private Set<Long> topicos;

	public TemaDTO() {
	}

	public TemaDTO(Tema tema) {
		this.setId(tema.getId());
		this.setNome(tema.getNome());
		this.setTopicos(tema.getTopicos().stream().map(Topico::getId).collect(Collectors.toSet()));
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Long> getTopicos() {
		return topicos;
	}

	public void setTopicos(Set<Long> topicos) {
		this.topicos = topicos;
	}
}
