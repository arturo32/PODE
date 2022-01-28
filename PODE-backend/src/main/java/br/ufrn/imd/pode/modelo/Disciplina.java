package br.ufrn.imd.pode.modelo;

import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "disciplina")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Disciplina extends ModeloAbstrato<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCIPLINA")
	@SequenceGenerator(name = "SEQ_DISCIPLINA", sequenceName = "id_seq_disciplina", allocationSize = 1)
	protected Long id;

	@NotNull
	@NotBlank
//	@Column(unique = true)
	protected String codigo;

	//	@NotNull
//	@NotBlank
	@Column(length = 1024)
	protected String nome;

	@NotNull
	protected Integer ch;

	@Column(length = 1024)
	private String prerequisitos;

	public Disciplina() {
	}

	public Disciplina(String codigo, String nome, Integer ch) {
		this.codigo = codigo;
		this.nome = nome;
		this.ch = ch;
	}

	public Disciplina(DisciplinaDTO disciplina) {
		this.id = disciplina.getId();
		this.codigo = disciplina.getCodigo();
		this.nome = disciplina.getNome();
		this.ch = disciplina.getCh();
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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