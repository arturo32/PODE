package br.ufrn.imd.pode.repositorio;

import org.springframework.data.repository.NoRepositoryBean;

import br.ufrn.imd.pode.modelo.Vinculo;

@NoRepositoryBean
public interface VinculoRepositorio<T extends Vinculo> extends GenericoRepositorio<T, Long> {
}
