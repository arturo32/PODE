package br.ufrn.imd.pode.model;

import javax.persistence.*;

@Entity
@Table(name = "administrador")
public class Administrador extends Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADMNISTRADOR")
	@SequenceGenerator(name = "SEQ_ADMNISTRADOR", sequenceName = "id_seq_administrador", allocationSize = 1)
	private Long id;

	public Administrador() {
	}

	public Administrador(String nome, String email, String senha) {
		super(nome, email, senha);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
