package br.ufrn.imd.pode.repositorio;

import org.springframework.data.repository.NoRepositoryBean;

import br.ufrn.imd.pode.modelo.GradeCurricular;

@NoRepositoryBean
public interface GradeCurricularRepositorio<T extends GradeCurricular> extends GenericoRepositorio<T, Long> {
}
