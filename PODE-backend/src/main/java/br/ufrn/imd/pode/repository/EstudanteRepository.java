package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Estudante;

import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface EstudanteRepository extends GenericRepository<Estudante, Long> {

	public Optional<Estudante> findByAtivoIsTrueAndEmail(@NotNull @NotBlank String email);

	public Optional<Estudante> findByAtivoIsTrueAndNome(@NotNull @NotBlank String nome);
}
