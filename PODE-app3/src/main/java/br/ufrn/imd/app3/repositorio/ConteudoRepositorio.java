package br.ufrn.imd.app3.repositorio;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;

import br.ufrn.imd.app3.modelo.Conteudo;

import java.util.List;

@Repository
public interface ConteudoRepositorio extends DisciplinaRepositorio<Conteudo> {

}
