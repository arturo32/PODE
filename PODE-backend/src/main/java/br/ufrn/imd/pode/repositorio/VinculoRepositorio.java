package br.ufrn.imd.pode.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.modelo.Vinculo;

@Repository
public interface VinculoRepositorio<T extends Vinculo> extends GenericoRepositorio<T, Long> {
}
