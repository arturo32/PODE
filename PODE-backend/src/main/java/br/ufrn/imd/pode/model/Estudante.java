package br.ufrn.imd.pode.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "estudante")
public class Estudante extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTUDANTE")
    @SequenceGenerator(name = "SEQ_ESTUDANTE", sequenceName = "id_seq_estudante", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToMany
    private Set<Vinculo> vinculos;

    public Estudante() {
    }

    public Estudante(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<Vinculo> getVinculos() {
        return vinculos;
    }

    public void setVinculos(Set<Vinculo> vinculos) {
        this.vinculos = vinculos;
    }
}
