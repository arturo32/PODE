package br.ufrn.imd.app3.repositorio;

import br.ufrn.imd.app3.modelo.Tema;
import br.ufrn.imd.app3.modelo.Topico;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepositorio extends GenericoRepositorio<Topico, Long> {

}
