package br.ufrn.imd.app3.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import br.ufrn.imd.pode.modelo.ModeloAbstrato;

@Entity
@Table(name = "tema")
public class Tema extends ModeloAbstrato<Long> {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "SEQ_TEMA"
	)
	@SequenceGenerator(
			name = "SEQ_TEMA",
			sequenceName = "id_seq_tema",
			allocationSize = 1
	)
	protected Long id;

	@NotNull
	@NotBlank
	protected String nome;

	@OneToMany
	@NotNull
	private Set<Topico> topicos;

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

	public Set<Topico> getTopicos() {
		return topicos;
	}

	public void setTopicos(Set<Topico> topicos) {
		this.topicos = topicos;
	}
}
