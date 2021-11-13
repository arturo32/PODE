package br.ufrn.imd.pode.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @ManyToOne
    private Enfase enfase;

    @NotNull
    @ManyToOne
    private PlanoCurso planoCurso;

    public Vinculo() {
    }

    public Vinculo(String matricula, Integer periodoInicial, Integer periodoAtual, Curso curso) {
        this.matricula = matricula;
        this.periodoInicial = periodoInicial;
        this.periodoAtual = periodoAtual;
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
}