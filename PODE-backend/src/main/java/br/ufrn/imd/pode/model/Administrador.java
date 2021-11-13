package br.ufrn.imd.pode.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador extends Usuario {

    public Administrador() {
    }

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
    }

}
