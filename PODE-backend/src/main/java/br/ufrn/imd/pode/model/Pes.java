package br.ufrn.imd.pode.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pes")
public class Pes extends AbstractModel<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PES")
	@SequenceGenerator(name = "SEQ_PES", sequenceName = "id_seq_pes", allocationSize = 1)
	private Long id;

	@NotNull
	@NotBlank
	private String nome;

	@NotNull
	// Carga horária mínima
	private Integer chm;

	@NotNull
	// Carga horária obrigatória
	private Integer cho;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "pes_disciplina_obrigatoria", joinColumns = {
			@JoinColumn(name = "pes_id") }, inverseJoinColumns = { @JoinColumn(name = "disciplina_id") })
	private Set<Disciplina> disciplinasObrigatorias = new HashSet<>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "pes_disciplina_optativa", joinColumns = { @JoinColumn(name = "pes_id") }, inverseJoinColumns = {
			@JoinColumn(name = "disciplina_id") })
	private Set<Disciplina> disciplinasOptativas = new HashSet<>();

	public Pes() {
		this.disciplinasObrigatorias = new HashSet<>();
		this.disciplinasOptativas = new HashSet<>();
	}

	public Pes(String nome, Integer chm, Integer cho) {
		this.nome = nome;
		this.chm = chm;
		this.cho = cho;
	}

	public Pes(String nome, Integer chm, Integer cho, Set<Disciplina> disciplinasObrigatorias,
			Set<Disciplina> disciplinasOptativas) {
		this.nome = nome;
		this.chm = chm;
		this.cho = cho;
		this.disciplinasObrigatorias = disciplinasObrigatorias;
		this.disciplinasOptativas = disciplinasOptativas;
	}

	@Override
	public Long getId() {
		return this.id;
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

	public Integer getChm() {
		return this.chm;
	}

	public void setChm(Integer chm) {
		this.chm = chm;
	}

	public Integer getCho() {
		return this.cho;
	}

	public void setCho(Integer cho) {
		this.cho = cho;
	}

	public Set<Disciplina> getDisciplinasObrigatorias() {
		return this.disciplinasObrigatorias;
	}

	public void setDisciplinasObrigatorias(Set<Disciplina> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<Disciplina> getDisciplinasOptativas() {
		return this.disciplinasOptativas;
	}

	public void setDisciplinasOptativas(Set<Disciplina> disciplinasOptativas) {
		this.disciplinasOptativas = disciplinasOptativas;
	}

	public void adicionarDisciplinaObrigatoria(Disciplina disciplina) {
		this.disciplinasObrigatorias.add(disciplina);
	}

	public void adicionarDisciplinaOptativa(Disciplina disciplina) {
		this.disciplinasOptativas.add(disciplina);
	}
}