package br.ufrn.imd.pode.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "plano_curso")
public class PlanoCurso extends AbstractModel<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLANO_CURSO")
	@SequenceGenerator(name = "SEQ_PLANO_CURSO", sequenceName = "id_seq_plano_curso", allocationSize = 1)
	private Long id;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "disciplinas_cursadas",
			joinColumns = {@JoinColumn(name = "plano_curso_id")},
			inverseJoinColumns = {@JoinColumn(name = "disciplina_periodo_id")})
	private Set<DisciplinaPeriodo> disciplinasCursadas;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "disciplinas_pendentes",
			joinColumns = {@JoinColumn(name = "plano_curso_id")},
			inverseJoinColumns = {@JoinColumn(name = "disciplina_periodo_id")})
	private Set<DisciplinaPeriodo> disciplinasPendentes;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "pes_interesse",
			joinColumns = {@JoinColumn(name = "plano_curso_id")},
			inverseJoinColumns = {@JoinColumn(name = "pes_id")})
	private Set<Pes> pesInteresse;

	public PlanoCurso() {
	}

	public PlanoCurso(Set<DisciplinaPeriodo> disciplinasCursadas, Set<DisciplinaPeriodo> disciplinasPendentes, Set<Pes> pesInteresse) {
		this.disciplinasCursadas = disciplinasCursadas;
		this.disciplinasPendentes = disciplinasPendentes;
		this.pesInteresse = pesInteresse;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Set<DisciplinaPeriodo> getDisciplinasCursadas() {
		return disciplinasCursadas;
	}

	public void setDisciplinasCursadas(Set<DisciplinaPeriodo> disciplinasCursadas) {
		this.disciplinasCursadas = disciplinasCursadas;
	}

	public Set<DisciplinaPeriodo> getDisciplinasPendentes() {
		return disciplinasPendentes;
	}

	public void setDisciplinasPendentes(Set<DisciplinaPeriodo> disciplinasPendentes) {
		this.disciplinasPendentes = disciplinasPendentes;
	}

	public Set<Pes> getPesInteresse() {
		return pesInteresse;
	}

	public void setPesInteresse(Set<Pes> pesInteresse) {
		this.pesInteresse = pesInteresse;
	}
}
