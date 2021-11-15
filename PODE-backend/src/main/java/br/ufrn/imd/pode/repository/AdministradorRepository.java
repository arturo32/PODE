package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Administrador;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends GenericRepository<Administrador, Long> {
}
