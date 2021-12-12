package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Administrador;
import br.ufrn.imd.pode.model.Estudante;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends GenericRepository<Administrador, Long> {

	Optional<Administrador> findByAtivoIsTrueAndEmail(@NotNull @NotBlank String email);

	Optional<Administrador> findByAtivoIsTrueAndNome(@NotNull @NotBlank String nome);
}
