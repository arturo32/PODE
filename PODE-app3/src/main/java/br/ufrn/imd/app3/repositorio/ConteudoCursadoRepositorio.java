package br.ufrn.imd.app3.repositorio;

import br.ufrn.imd.app3.modelo.Conteudo;
import br.ufrn.imd.app3.modelo.ConteudoCursado;
import br.ufrn.imd.pode.repositorio.DisciplinaCursadaRepositorio;
import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;
import org.springframework.stereotype.Repository;

@Repository
public interface ConteudoCursadoRepositorio extends DisciplinaCursadaRepositorio<ConteudoCursado> {

}
