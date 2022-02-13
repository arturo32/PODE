package br.ufrn.imd.app3.repositorio;

import org.springframework.stereotype.Repository;

import java.util.Set;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import br.ufrn.imd.app3.modelo.Tema;

@Repository
public interface TemaRepositorio extends GenericoRepositorio<Tema, Long> {
    Set<Tema> findTemasByNomeAndAtivoIsTrue(String nome);
}
