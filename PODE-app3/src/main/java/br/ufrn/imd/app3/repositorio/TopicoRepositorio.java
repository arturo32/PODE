package br.ufrn.imd.app3.repositorio;

import br.ufrn.imd.app3.modelo.Tema;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import br.ufrn.imd.app3.modelo.Topico;

import java.util.Set;

@Repository
public interface TopicoRepositorio extends GenericoRepositorio<Topico, Long> {

	Set<Topico> findTopicosByNomeAndAtivoIsTrue(String nome);
}
