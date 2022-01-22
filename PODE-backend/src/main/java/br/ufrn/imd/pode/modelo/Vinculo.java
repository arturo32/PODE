package br.ufrn.imd.pode.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vinculo")
public class Vinculo extends ModeloAbstrato<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VINCULO")
	@SequenceGenerator(name = "SEQ_VINCULO", sequenceName = "id_seq_vinculo", allocationSize = 1)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String matricula;

	@NotNull
	private Integer periodoInicialAno;

	@NotNull
	private Integer periodoInicialPeriodo;

	@NotNull
	private Integer periodoAtualAno;

	@NotNull
	private Integer periodoAtualPeriodo;

	@NotNull
	@ManyToOne
	private Curso curso;

	@ManyToOne
	private Enfase enfase;

	@NotNull
	@ManyToOne
	private PlanoCurso planoCurso;

	@ManyToOne
	@JoinColumn(name = "estudante_id")
	private Estudante estudante;

	public Vinculo() {
	}

	public Vinculo(String matricula, Integer periodoInicialAno, Integer periodoAtualAno, Curso curso) {
		this.matricula = matricula;
		this.periodoInicialAno = periodoInicialAno;
		this.periodoAtualAno = periodoAtualAno;
		this.curso = curso;
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

	public Integer getPeriodoInicialAno() {
		return periodoInicialAno;
	}

	public void setPeriodoInicialAno(Integer periodoInicialAno) {
		this.periodoInicialAno = periodoInicialAno;
	}

	public Integer getPeriodoInicialPeriodo() {
		return periodoInicialPeriodo;
	}

	public void setPeriodoInicialPeriodo(Integer periodoInicialPeriodo) {
		this.periodoInicialPeriodo = periodoInicialPeriodo;
	}

	public Integer getPeriodoAtualAno() {
		return periodoAtualAno;
	}

	public void setPeriodoAtualAno(Integer periodoAtualAno) {
		this.periodoAtualAno = periodoAtualAno;
	}

	public Integer getPeriodoAtualPeriodo() {
		return periodoAtualPeriodo;
	}

	public void setPeriodoAtualPeriodo(Integer periodoAtualPeriodo) {
		this.periodoAtualPeriodo = periodoAtualPeriodo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Enfase getEnfase() {
		return enfase;
	}

	public void setEnfase(Enfase enfase) {
		this.enfase = enfase;
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

	@JsonIgnore
	public Integer getPeriodoAtual() {
		int ano_diff = getPeriodoAtualAno() - getPeriodoInicialAno();
		int periodos = ano_diff * 2;
		if (getPeriodoAtualPeriodo().equals(getPeriodoInicialPeriodo())) {
			periodos += 1;
		} else {
			if (getPeriodoAtualPeriodo() > getPeriodoInicialPeriodo()) {
				periodos += 2;
			}
		}
		return periodos;
	}
}
