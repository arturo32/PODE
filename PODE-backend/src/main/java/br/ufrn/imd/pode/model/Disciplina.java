package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.model.dto.DisciplinaDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "disciplina")
public class Disciplina extends AbstractModel<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCIPLINA")
	@SequenceGenerator(name = "SEQ_DISCIPLINA", sequenceName = "id_seq_disciplina", allocationSize = 1)
	private Long id;

	@NotNull
	@NotBlank
	@Column(unique = true)
	private String codigo;

	@NotNull
	@NotBlank
	private String nome;

	@NotNull
	private Integer ch;

	private String prerequisitos;

	private String corequisitos;

	private String equivalentes;

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

	public String getCorequisitos() {
		return corequisitos;
	}

	public void setCorequisitos(String corequisitos) {
		this.corequisitos = corequisitos;
	}

	public String getEquivalentes() {
		return equivalentes;
	}

	public void setEquivalentes(String equivalentes) {
		this.equivalentes = equivalentes;
	}
}