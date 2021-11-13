package br.ufrn.imd.pode.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "disciplina_semestre")
public class DisciplinaSemestre extends AbstractModel<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCIPLINA_SEMESTRE")
    @SequenceGenerator(name = "SEQ_DISCIPLINA_SEMESTRE", sequenceName = "id_seq_disciplina_semestre", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="disciplina_id")
    private Disciplina disciplina;

    @NotNull
    private Integer semestre;

    public DisciplinaSemestre() {
    }

    public DisciplinaSemestre(Disciplina disciplina, Integer semestre) {
        this.disciplina = disciplina;
        this.semestre = semestre;
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

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }
}