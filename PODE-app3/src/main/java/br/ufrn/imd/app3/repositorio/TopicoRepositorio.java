package br.ufrn.imd.app3.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import br.ufrn.imd.app3.modelo.Topico;

@Repository
public interface TopicoRepositorio extends GenericoRepositorio<Topico, Long> {

}
