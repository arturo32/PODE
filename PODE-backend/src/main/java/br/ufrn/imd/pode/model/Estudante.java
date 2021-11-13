package br.ufrn.imd.pode.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "estudante")
public class Estudante extends Usuario {

    @NotNull
    @OneToMany
    private Set<Vinculo> vinculos;

    public Estudante() {
    }

    public Estudante(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Set<Vinculo> getVinculos() {
        return vinculos;
    }

    public void setVinculos(Set<Vinculo> vinculos) {
        this.vinculos = vinculos;
    }
}
