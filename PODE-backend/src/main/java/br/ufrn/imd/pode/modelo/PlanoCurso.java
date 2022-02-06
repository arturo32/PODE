package br.ufrn.imd.pode.modelo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "planocurso")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PlanoCurso extends ModeloAbstrato<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLANO_CURSO")
	@SequenceGenerator(name = "SEQ_PLANO_CURSO", sequenceName = "id_seq_plano_curso", allocationSize = 1)
	protected Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "planocurso_disciplina_cursadas", joinColumns = {
			@JoinColumn(name = "planocurso_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	private Set<DisciplinaCursada> disciplinasCursadas;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "planocurso_disciplina_pendentes", joinColumns = {
			@JoinColumn(name = "planocurso_id")}, inverseJoinColumns = {@JoinColumn(name = "disciplina_id")})
	private Set<DisciplinaCursada> disciplinasPendentes;


	public Set<DisciplinaCursada> getDisciplinasCursadas() {
		return disciplinasCursadas;
	}

	public void setDisciplinasCursadas(Set<DisciplinaCursada> disciplinasCursadas) {
		this.disciplinasCursadas = disciplinasCursadas;
	}

	public Set<DisciplinaCursada> getDisciplinasPendentes() {
		return disciplinasPendentes;
	}

	public void setDisciplinasPendentes(Set<DisciplinaCursada> disciplinasPendentes) {
		this.disciplinasPendentes = disciplinasPendentes;
	}
}
