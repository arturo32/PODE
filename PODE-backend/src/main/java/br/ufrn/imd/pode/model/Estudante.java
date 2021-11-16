package br.ufrn.imd.pode.model;

import br.ufrn.imd.pode.model.dto.EstudanteDTO;
import br.ufrn.imd.pode.model.dto.VinculoDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "estudante")
public class Estudante extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTUDANTE")
    @SequenceGenerator(name = "SEQ_ESTUDANTE", sequenceName = "id_seq_estudante", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToMany(mappedBy = "estudante", cascade = {CascadeType.ALL})
    private Set<Vinculo> vinculos;

    public Estudante() {
        this.vinculos = new HashSet<>();
    }

    public Estudante(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Estudante(EstudanteDTO estudante) {
        this.id = estudante.getId();
        for(VinculoDTO vinculo : estudante.getVinculos()){
            this.vinculos.add(new Vinculo(vinculo));
        }

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
