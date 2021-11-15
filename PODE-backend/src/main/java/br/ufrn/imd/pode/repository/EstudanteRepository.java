package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Estudante;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudanteRepository extends GenericRepository<Estudante, Long> {
}
