package br.ufrn.imd.app1.modelo;

import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.PlanoCurso;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
		name = "planocursopes"
)
public class PlanoCursoPes extends PlanoCurso {
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "planocursopes_interesse_pes", joinColumns = {
			@JoinColumn(name = "planocursopes_id") }, inverseJoinColumns = { @JoinColumn(name = "pes_id") })
	private List<Pes> gradesParalelas;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "plano_curso_disciplina_cursada", joinColumns = {
		@JoinColumn(name = "plano_curso_id")}, inverseJoinColumns = {
		@JoinColumn(name = "disciplina_periodo_id")})
	protected Set<DisciplinaPeriodo> disciplinasCursadas;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "plano_curso_disciplina_pendente", joinColumns = {
			@JoinColumn(name = "plano_curso_id")}, inverseJoinColumns = {
			@JoinColumn(name = "disciplina_periodo_id")})
	protected Set<DisciplinaBTI> disciplinasPendentes;

	public List<Pes> getGradesParalelas() {
		return gradesParalelas;
	}

	public void setGradesParalelas(List<Pes> gradesParalelas) {
		this.gradesParalelas = gradesParalelas;
	}

	@Override
	public Set<DisciplinaInterface> getDisciplinasCursadas() {
		return new HashSet<>(disciplinasCursadas);
	}

	@Override
	public Set<DisciplinaInterface> getDisciplinasPendentes() {
		return new HashSet<>(disciplinasPendentes);
	}
}