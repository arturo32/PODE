package br.ufrn.imd.pode.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vinculo")
public abstract class Vinculo extends ModeloAbstrato<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VINCULO")
	@SequenceGenerator(name = "SEQ_VINCULO", sequenceName = "id_seq_vinculo", allocationSize = 1)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String matricula;

	@ManyToOne
	private @NotNull GradeCurricular gradeCurricula;

	@NotNull
	@ManyToOne
	private PlanoCurso planoCurso;

	@ManyToOne
	@JoinColumn(name = "estudante_id")
	private Estudante estudante;

	public Vinculo() {
	}

	public Vinculo(String matricula, @NotNull GradeCurricular gradeCurricula) {
		this.matricula = matricula;
		this.gradeCurricula = gradeCurricula;
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

	public @NotNull GradeCurricular getGradeCurricula() {
		return gradeCurricula;
	}

	public void setGradeCurricula(@NotNull GradeCurricular curso) {
		this.gradeCurricula = curso;
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