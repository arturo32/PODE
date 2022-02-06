package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DisciplinaCursadaRepositorio<T extends DisciplinaCursada> extends GenericoRepositorio<T, Long> {
}
