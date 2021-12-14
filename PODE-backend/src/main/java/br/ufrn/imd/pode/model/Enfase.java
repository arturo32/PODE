package br.ufrn.imd.pode.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "enfase")
public class Enfase extends AbstractModel<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENFASE")
	@SequenceGenerator(name = "SEQ_ENFASE", sequenceName = "id_seq_enfase", allocationSize = 1)
	private Long id;

	@NotNull
	@NotBlank
	@Column(unique = true)
	private String nome;

	@NotNull
	@OneToOne
	private Curso curso;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "enfase_disciplina_obrigatoria", joinColumns = {
			@JoinColumn(name = "enfase_id") }, inverseJoinColumns = { @JoinColumn(name = "disciplina_periodo_id") })
	private Set<DisciplinaPeriodo> disciplinasObrigatorias = new HashSet<>();

	public Enfase() {
		this.disciplinasObrigatorias = new HashSet<>();
	}

	public Enfase(String nome, Curso curso) {
		this.nome = nome;
		this.curso = curso;
	}

	public Enfase(String nome, Curso curso, Set<DisciplinaPeriodo> disciplinasObrigatorias) {
		this.nome = nome;
		this.curso = curso;
		this.disciplinasObrigatorias = disciplinasObrigatorias;
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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Integer getChEspecifica() {
		Integer cho_especifica = 0;
		for (DisciplinaPeriodo disciplina : this.disciplinasObrigatorias) {
			cho_especifica += disciplina.getDisciplina().getCh();
		}
		return cho_especifica;
	}

	public Integer getChm() {
		return this.curso.getChm();
	}

	public Integer getCho() {
		return this.curso.getCho() + this.getChEspecifica();
	}

	public Integer getChom() {
		return this.curso.getChom() - this.getChEspecifica();
	}

	public Integer getChcm() {
		return this.curso.getChcm();
	}

	public Integer getChem() {
		return this.curso.getChem();
	}

	public Integer getChmaxp() {
		return this.curso.getChmaxp();
	}

	public Integer getChminp() {
		return this.curso.getChminp();
	}

	public Integer getPrazoMinimo() {
		return this.curso.getPrazoMinimo();
	}

	public Integer getPrazoMaximo() {
		return this.curso.getPrazoMaximo();
	}

	public Integer getPrazoEsperado() {
		return this.curso.getPrazoEsperado();
	}

	public Set<DisciplinaPeriodo> getDisciplinasObrigatorias() {
		Set<DisciplinaPeriodo> resultado = this.curso.getDisciplinasObrigatorias();
		resultado.addAll(this.disciplinasObrigatorias);
		return resultado;
	}

	public Set<Disciplina> getDisciplinasOptativas() {
		Set<Disciplina> resultado = this.curso.getDisciplinasOptativas();
		Set<Disciplina> obrigatorias = new ArrayList<>(this.disciplinasObrigatorias).stream()
				.map(DisciplinaPeriodo::getDisciplina).collect(Collectors.toSet());
		resultado.removeAll(obrigatorias);
		return resultado;
	}

	public void setDisciplinasObrigatorias(Set<DisciplinaPeriodo> disciplinasObrigatorias) {
		this.disciplinasObrigatorias = disciplinasObrigatorias;
	}

	public Set<DisciplinaPeriodo> getDisciplinasObrigatoriasEspecificas() {
		return this.disciplinasObrigatorias;
	}

	public void adicionarDisciplinaObrigatoria(DisciplinaPeriodo disciplinaPeriodo) {
		this.disciplinasObrigatorias.add(disciplinaPeriodo);
	}

}