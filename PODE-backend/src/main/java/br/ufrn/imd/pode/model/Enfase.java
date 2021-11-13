package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.interfaces.IGradeCurricularPrimaria;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "enfase")
public class Enfase extends AbstractModel<Long> implements IGradeCurricularPrimaria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENFASE")
    @SequenceGenerator(name = "SEQ_ENFASE", sequenceName = "id_seq_enfase", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nome;

    @OneToOne
    private Curso curso;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="enfase_obrigatorias",
            joinColumns={@JoinColumn(name="enfase_id")},
            inverseJoinColumns={@JoinColumn(name="disciplina_semestre_id")})
    private Set<DisciplinaSemestre> disciplinasObrigatorias;

    public Enfase() {
    }

    public Enfase(String nome, Curso curso, Set<DisciplinaSemestre> disciplinasObrigatorias) {
        this.nome = nome;
        this.curso = curso;
        this.disciplinasObrigatorias = disciplinasObrigatorias;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public Integer getChm() {
        return this.curso.getChm();
    }

    @Override
    public Integer getCho() {
        //TODO
        return this.curso.getCho();
    }

    @Override
    public Integer getChom() {
        //TODO
        return this.curso.getChom();
    }

    @Override
    public Integer getChcm() {
        //TODO
        return this.curso.getChcm();
    }

    @Override
    public Integer getChem() {
        //TODO
        return this.curso.getChem();
    }

    @Override
    public Integer getChmp() {
        //TODO
        return this.curso.getChmp();
    }

    @Override
    public Integer getPrazoMinimo() {
        return this.curso.getPrazoMinimo();
    }

    @Override
    public Integer getPrazoMaximo() {
        return this.curso.getPrazoMaximo();
    }

    @Override
    public Integer getPrazoEsperado() {
        return this.curso.getPrazoEsperado();
    }

    @Override
    public Set<DisciplinaSemestre> getDisciplinasObrigatorias() {
        //TODO
        return this.disciplinasObrigatorias;
    }

    @Override
    public Set<Disciplina> getDisciplinasOptativas() {
        //TODO
        return this.curso.getDisciplinasOptativas();
    }

    @Override
    public Boolean concluida(Set<Disciplina> disciplinas) {
        // TODO
        return false;
    }

    public void setDisciplinasObrigatorias(Set<DisciplinaSemestre> disciplinasObrigatorias) {
        //TODO
        this.disciplinasObrigatorias = disciplinasObrigatorias;
    }
}