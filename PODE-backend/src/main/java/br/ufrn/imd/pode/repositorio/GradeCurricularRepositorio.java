package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.GradeCurricular;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GradeCurricularRepositorio<T extends GradeCurricular> extends GenericoRepositorio<T, Long> {
}
