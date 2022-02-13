package br.ufrn.imd.app3.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.ufrn.imd.pode.modelo.Disciplina;

@Entity
@Table(name = "conteudo")
public class Conteudo extends Disciplina {

	@ManyToOne
	@NotNull
	private Tema tema;

	@ManyToOne
	@NotNull
	private Topico topico;

	@NotNull
	@NotBlank
	private String nivel;

	public Conteudo() {}

	public Conteudo(String codigo, String nome, int ch) {
		this.codigo = codigo;
		this.nome = nome;
		this.ch = ch;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

}
