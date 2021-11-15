package br.ufrn.imd.pode.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "disciplina_periodo")
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