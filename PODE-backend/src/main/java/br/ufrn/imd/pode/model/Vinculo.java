package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.model.dto.EnfaseDTO;
import br.ufrn.imd.pode.model.dto.VinculoDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vinculo")
public class Vinculo extends AbstractModel<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VINCULO")
	@SequenceGenerator(name = "SEQ_VINCULO", sequenceName = "id_seq_vinculo", allocationSize = 1)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String matricula;

	@NotNull
	private Integer periodoInicial;

	@NotNull
	private Integer periodoAtual;

	@NotNull
	@ManyToOne
	private Curso curso;

	@ManyToMany
	@JoinTable(name = "vinculo_enfase",
			joinColumns = {@JoinColumn(name = "vinculo_id")},
			inverseJoinColumns = {@JoinColumn(name = "enfase_id")})
	private List<Enfase> enfase;

	@NotNull
	@ManyToOne
	private PlanoCurso planoCurso;

	@ManyToOne
	@JoinColumn(name = "estudante_id")
	private Estudante estudante;


	public Vinculo() {
		this.enfase = new ArrayList<>();
	}

	public Vinculo(String matricula, Integer periodoInicial, Integer periodoAtual, Curso curso) {
		this.matricula = matricula;
		this.periodoInicial = periodoInicial;
		this.periodoAtual = periodoAtual;
		this.curso = curso;
	}

	public Vinculo(VinculoDTO vinculo) {
		this.matricula = vinculo.getMatricula();
		this.periodoInicial = vinculo.getPeriodoInicial();
		this.periodoAtual = vinculo.getPeriodoAtual();
		this.curso = new Curso(vinculo.getCurso());
		for (EnfaseDTO enfase : vinculo.getEnfase()) {
			this.enfase.add(new Enfase(enfase));
		}
		this.planoCurso = new PlanoCurso(vinculo.getPlanoCurso());
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

	public Integer getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Integer periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Integer getPeriodoAtual() {
		return periodoAtual;
	}

	public void setPeriodoAtual(Integer periodoAtual) {
		this.periodoAtual = periodoAtual;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso gradePrimaria) {
		this.curso = gradePrimaria;
	}

	public List<Enfase> getEnfase() {
		return enfase;
	}

	public void setEnfase(List<Enfase> enfase) {
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
}