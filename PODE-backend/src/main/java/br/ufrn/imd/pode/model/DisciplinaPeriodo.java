package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.model.dto.DisciplinaPeriodoDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "disciplinaperiodo")
public class DisciplinaPeriodo extends AbstractModel<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCIPLINA_PERIODO")
	@SequenceGenerator(name = "SEQ_DISCIPLINA_PERIODO", sequenceName = "id_seq_disciplina_periodo", allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "disciplina_id")
	private Disciplina disciplina;

	@NotNull
	private Integer periodo;

	public DisciplinaPeriodo() {
	}

	public DisciplinaPeriodo(Disciplina disciplina, Integer periodo) {
		this.disciplina = disciplina;
		this.periodo = periodo;
	}

	public DisciplinaPeriodo(DisciplinaPeriodoDTO disciplinaPeriodo) {
		this.id = disciplinaPeriodo.getId();
		this.disciplina = new Disciplina(disciplinaPeriodo.getDisciplina());
		this.periodo = disciplinaPeriodo.getPeriodo();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}