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

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "disciplina_prerequisitos",
			joinColumns = {@JoinColumn(name = "disciplina_id")},
			inverseJoinColumns = {@JoinColumn(name = "prerequisito_id")})
	private Set<Disciplina> prerequisitos = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "disciplina_corequisitos",
			joinColumns = {@JoinColumn(name = "disciplina_id")},
			inverseJoinColumns = {@JoinColumn(name = "corequisito_id")})
	private Set<Disciplina> corequisitos = new HashSet<>();

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
		for (DisciplinaDTO prerequisito : disciplina.getPrerequisitos()) {
			this.prerequisitos.add(new Disciplina(prerequisito));
		}
		for (DisciplinaDTO corequisito : disciplina.getCorequisitos()) {
			this.corequisitos.add(new Disciplina(corequisito));
		}
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

	public Set<Disciplina> getPrerequisitos() {
		return prerequisitos;
	}

	public void setPrerequisitos(Set<Disciplina> prerequisitos) {
		this.prerequisitos = prerequisitos;
	}

	public Set<Disciplina> getCorequisitos() {
		return corequisitos;
	}

	public void setCorequisitos(Set<Disciplina> corequisitos) {
		this.corequisitos = corequisitos;
	}

	public String getEquivalentes() {
		return equivalentes;
	}

	public void setEquivalentes(String equivalentes) {
		this.equivalentes = equivalentes;
	}
}