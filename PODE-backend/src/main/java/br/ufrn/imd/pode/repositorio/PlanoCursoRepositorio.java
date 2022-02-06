package br.ufrn.imd.pode.repositorio;

import org.springframework.data.repository.NoRepositoryBean;

import br.ufrn.imd.pode.modelo.PlanoCurso;

@NoRepositoryBean
public interface PlanoCursoRepositorio<T extends PlanoCurso> extends GenericoRepositorio<T, Long> {
}
