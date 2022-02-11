package br.ufrn.imd.app3.repositorio;

import br.ufrn.imd.app3.modelo.Conteudo;
import br.ufrn.imd.app3.modelo.Tema;
import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepositorio extends GenericoRepositorio<Tema, Long> {

}
