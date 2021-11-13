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
}
