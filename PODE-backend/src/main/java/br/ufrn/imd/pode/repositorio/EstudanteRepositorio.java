package br.ufrn.imd.pode.repositorio;

import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import br.ufrn.imd.pode.modelo.Estudante;

@Repository
public interface EstudanteRepositorio extends GenericoRepositorio<Estudante, Long> {

	Optional<Estudante> findByAtivoIsTrueAndEmail(@NotNull @NotBlank String email);

	Optional<Estudante> findByAtivoIsTrueAndNome(@NotNull @NotBlank String nome);
}
