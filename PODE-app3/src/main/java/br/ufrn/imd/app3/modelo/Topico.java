package br.ufrn.imd.app3.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.ufrn.imd.pode.modelo.ModeloAbstrato;

@Entity
@Table(name = "topico")
public class Topico extends ModeloAbstrato<Long> {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "SEQ_TOPICO"
	)
	@SequenceGenerator(
			name = "SEQ_TOPICO",
			sequenceName = "id_seq_topico",
			allocationSize = 1
	)
	protected Long id;

	@NotNull
	@NotBlank
	protected String nome;

	@ManyToOne
	@NotNull
	private Tema tema;

	public Topico() {
	}

	public Topico(String nome, Tema tema) {
		this.nome = nome;
		this.tema = tema;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
}
