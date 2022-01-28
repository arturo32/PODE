package br.ufrn.imd.pode.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vinculo")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vinculo extends ModeloAbstrato<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VINCULO")
	@SequenceGenerator(name = "SEQ_VINCULO", sequenceName = "id_seq_vinculo", allocationSize = 1)
	protected Long id;

	@NotBlank
	@Column(unique = true)
	protected String matricula;

	@ManyToOne
	protected @NotNull GradeCurricular gradeCurricular;

	@NotNull
	@ManyToOne
	protected PlanoCurso planoCurso;

	@ManyToOne
	@JoinColumn(name = "estudante_id")
	protected Estudante estudante;

	public Vinculo() {
	}

	public Vinculo(String matricula, @NotNull GradeCurricular gradeCurricular) {
		this.matricula = matricula;
		this.gradeCurricular = gradeCurricular;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public @NotNull GradeCurricular getGradeCurricular() {
		return gradeCurricular;
	}

	public void setGradeCurricular(@NotNull GradeCurricular curso) {
		this.gradeCurricular = curso;
	}

	public PlanoCurso getPlanoCurso() {
		return planoCurso;
	}

	public void setPlanoCurso(PlanoCurso planoCurso) {
		this.planoCurso = planoCurso;
	}

	public Estudante getEstudante() {
		return estudante;
	}

	public void setEstudante(Estudante estudante) {
		this.estudante = estudante;
	}
}
