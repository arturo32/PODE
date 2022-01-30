package br.ufrn.imd.app1.repositorio;

import br.ufrn.imd.app1.modelo.Pes;
import br.ufrn.imd.pode.repositorio.GradeCurricularRepositorio;
import org.springframework.stereotype.Repository;

@Repository
public interface PesRepositorio extends GradeCurricularRepositorio<Pes> {
}
