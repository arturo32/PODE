package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.Vinculo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VinculoRepositorio<T extends Vinculo> extends GenericoRepositorio<T, Long> {
}
