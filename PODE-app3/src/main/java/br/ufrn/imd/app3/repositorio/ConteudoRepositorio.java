package br.ufrn.imd.app3.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;

import br.ufrn.imd.app3.modelo.Conteudo;

@Repository
public interface ConteudoRepositorio extends DisciplinaRepositorio<Conteudo> {

}
