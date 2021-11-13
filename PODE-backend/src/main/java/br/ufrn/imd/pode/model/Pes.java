package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.interfaces.IGradeCurricularSecundaria;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "pes")
public class Pes extends AbstractModel<Long> implements IGradeCurricularSecundaria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PES")
    @SequenceGenerator(name = "SEQ_PES", sequenceName = "id_seq_pes", allocationSize = 1)
    private Long id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    // carga horaria minima
    private Integer chm;

    @NotNull
    // carga horaria obrigatoria
    private Integer cho;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="pes_obrigatorias",
            joinColumns={@JoinColumn(name="pes_id")},
            inverseJoinColumns={@JoinColumn(name="disciplina_id")})
    private Set<Disciplina> disciplinasObrigatorias;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="pes_optativas",
            joinColumns={@JoinColumn(name="pes_id")},
            inverseJoinColumns={@JoinColumn(name="disciplina_id")})
    private Set<Disciplina> disciplinasOptativas;

    public Pes() {
    }

    public Pes(String nome, Integer chm, Integer cho, Set<Disciplina> disciplinasObrigatorias, Set<Disciplina> disciplinasOptativas) {
        this.nome = nome;
        this.chm = chm;
        this.cho = cho;
        this.disciplinasObrigatorias = disciplinasObrigatorias;
        this.disciplinasOptativas = disciplinasOptativas;
    }

    @Override
    public Long getId() {
        return this.id;
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

    @Override
    public Integer getChm() {
        return this.chm;
    }

    public void setChm(Integer chm) {
        this.chm = chm;
    }

    @Override
    public Integer getCho() {
        return this.cho;
    }

    public void setCho(Integer cho) {
        this.cho = cho;
    }

    @Override
    public Set<Disciplina> getDisciplinasObrigatorias() {
        return this.disciplinasObrigatorias;
    }

    public void setDisciplinasObrigatorias(Set<Disciplina> disciplinasObrigatorias) {
        this.disciplinasObrigatorias = disciplinasObrigatorias;
    }

    @Override
    public Set<Disciplina> getDisciplinasOptativas() {
        return this.disciplinasOptativas;
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