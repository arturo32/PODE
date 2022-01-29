package br.ufrn.imd.pode.modelo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planocurso")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PlanoCurso extends ModeloAbstrato<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLANO_CURSO")
	@SequenceGenerator(name = "SEQ_PLANO_CURSO", sequenceName = "id_seq_plano_curso", allocationSize = 1)
	protected Long id;

//	@ManyToMany(cascade = {CascadeType.ALL})
//	@JoinTable(name = "plano_curso_disciplina_cursada", joinColumns = {
//			@JoinColumn(name = "plano_curso_id")}, inverseJoinColumns = {
//			@JoinColumn(name = "disciplina_periodo_id")})
//	protected Set<Disciplina> disciplinasCursadas;
//
//	@ManyToMany(cascade = {CascadeType.ALL})
//	@JoinTable(name = "plano_curso_disciplina_pendente", joinColumns = {
//			@JoinColumn(name = "plano_curso_id")}, inverseJoinColumns = {
//			@JoinColumn(name = "disciplina_periodo_id")})
//	protected Set<Disciplina> disciplinasPendentes;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public abstract Set<DisciplinaInterface> getDisciplinasCursadas();

	public abstract Set<DisciplinaInterface> getDisciplinasPendentes();
}
