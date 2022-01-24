package br.ufrn.imd.pode.modelo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planocurso")
public class PlanoCurso extends ModeloAbstrato<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLANO_CURSO")
	@SequenceGenerator(name = "SEQ_PLANO_CURSO", sequenceName = "id_seq_plano_curso", allocationSize = 1)
	private Long id;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "plano_curso_disciplina_cursada", joinColumns = {
			@JoinColumn(name = "plano_curso_id")}, inverseJoinColumns = {
			@JoinColumn(name = "disciplina_periodo_id")})
	private Set<Disciplina> disciplinasCursadas;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "plano_curso_disciplina_pendente", joinColumns = {
			@JoinColumn(name = "plano_curso_id")}, inverseJoinColumns = {
			@JoinColumn(name = "disciplina_periodo_id")})
	private Set<Disciplina> disciplinasPendentes;

	public PlanoCurso() {
		this.disciplinasCursadas = new HashSet<>();
		this.disciplinasPendentes = new HashSet<>();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Set<Disciplina> getDisciplinasCursadas() {
		return disciplinasCursadas;
	}

	public void setDisciplinasCursadas(Set<Disciplina> disciplinasCursadas) {
		this.disciplinasCursadas = disciplinasCursadas;
	}

	public Set<Disciplina> getDisciplinasPendentes() {
		return disciplinasPendentes;
	}

	public void setDisciplinasPendentes(Set<Disciplina> disciplinasPendentes) {
		this.disciplinasPendentes = disciplinasPendentes;
	}
}
