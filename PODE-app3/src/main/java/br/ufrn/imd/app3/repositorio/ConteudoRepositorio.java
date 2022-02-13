package br.ufrn.imd.app3.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;

import br.ufrn.imd.app3.modelo.Conteudo;


@Repository
public interface ConteudoRepositorio extends DisciplinaRepositorio<Conteudo> {

	List<Conteudo> findConteudosByAtivoIsTrueAndTema_Id(Long tema_id);

	List<Conteudo> findConteudosByAtivoIsTrueAndTopico_Id(Long topico_id);
}
