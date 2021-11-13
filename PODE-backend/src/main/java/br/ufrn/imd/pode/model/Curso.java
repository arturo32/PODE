package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.interfaces.IGradeCurricularPrimaria;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso extends AbstractModel<Long> implements IGradeCurricularPrimaria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CURSO")
    @SequenceGenerator(name = "SEQ_CURSO", sequenceName = "id_seq_curso", allocationSize = 1)
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String codigo;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    // carga horaria minima
    private Integer chm;

    @NotNull
    // carga horaria obrigatoria
    private Integer cho;

    @NotNull
    // carga horaria optativa minima
    private Integer chom;

    @NotNull
    // carga horaria complementar minima
    private Integer chcm;

    @NotNull
    // carga horaria eletiva maxima
    private Integer chem;

    @NotNull
    // carga horaria maxima por periodo
    private Integer chmp;

    @NotNull
    private Integer prazoMinimo;

    @NotNull
    private Integer prazoMaximo;

    @NotNull
    private Integer prazoEsperado;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="curso_obrigatorias",
            joinColumns={@JoinColumn(name="curso_id")},
            inverseJoinColumns={@JoinColumn(name="disciplina_semestre_id")})
    private Set<DisciplinaSemestre> disciplinasObrigatorias;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="curso_optativas",
            joinColumns={@JoinColumn(name="curso_id")},
            inverseJoinColumns={@JoinColumn(name="disciplina_id")})
    private Set<Disciplina> disciplinasOptativas;

    public Curso() {
    }

    public Curso(String codigo, String nome, Integer chm, Integer cho, Integer chom, Integer chcm, Integer chem, Integer chmp, Integer prazoMinimo, Integer prazoMaximo, Integer prazoEsperado, Set<DisciplinaSemestre> disciplinasObrigatorias, Set<Disciplina> disciplinasOptativas) {
        this.codigo = codigo;
        this.nome = nome;
        this.chm = chm;
        this.cho = cho;
        this.chom = chom;
        this.chcm = chcm;
        this.chem = chem;
        this.chmp = chmp;
        this.prazoMinimo = prazoMinimo;
        this.prazoMaximo = prazoMaximo;
        this.prazoEsperado = prazoEsperado;
        this.disciplinasObrigatorias = disciplinasObrigatorias;
        this.disciplinasOptativas = disciplinasOptativas;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public Integer getChm() {
        return chm;
    }

    public void setChm(Integer chm) {
        this.chm = chm;
    }

    @Override
    public Integer getCho() {
        return cho;
    }

    public void setCho(Integer cho) {
        this.cho = cho;
    }

    @Override
    public Integer getChom() {
        return chom;
    }

    public void setChom(Integer chom) {
        this.chom = chom;
    }

    @Override
    public Integer getChcm() {
        return chcm;
    }

    public void setChcm(Integer chcm) {
        this.chcm = chcm;
    }

    @Override
    public Integer getChem() {
        return chem;
    }

    public void setChem(Integer chem) {
        this.chem = chem;
    }

    @Override
    public Integer getChmp() {
        return chmp;
    }

    public void setChmp(Integer chmp) {
        this.chmp = chmp;
    }

    @Override
    public Integer getPrazoMinimo() {
        return prazoMinimo;
    }

    public void setPrazoMinimo(Integer prazoMinimo) {
        this.prazoMinimo = prazoMinimo;
    }

    @Override
    public Integer getPrazoMaximo() {
        return prazoMaximo;
    }

    public void setPrazoMaximo(Integer prazoMaximo) {
        this.prazoMaximo = prazoMaximo;
    }

    @Override
    public Integer getPrazoEsperado() {
        return prazoEsperado;
    }

    public void setPrazoEsperado(Integer prazoEsperado) {
        this.prazoEsperado = prazoEsperado;
    }

    @Override
    public Set<DisciplinaSemestre> getDisciplinasObrigatorias() {
        return disciplinasObrigatorias;
    }

    public void setDisciplinasObrigatorias(Set<DisciplinaSemestre> disciplinasObrigatorias) {
        this.disciplinasObrigatorias = disciplinasObrigatorias;
    }

    @Override
    public Set<Disciplina> getDisciplinasOptativas() {
        return disciplinasOptativas;
    }

    public void setDisciplinasOptativas(Set<Disciplina> disciplinasOptativas) {
        this.disciplinasOptativas = disciplinasOptativas;
    }

    @Override
    public Boolean concluida(Set<Disciplina> disciplinas) {
        // TODO
        return false;
    }
}