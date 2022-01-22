package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.Administrador;

import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface AdministradorRepositorio extends GenericoRepositorio<Administrador, Long> {

	public Optional<Administrador> findByAtivoIsTrueAndEmail(@NotNull @NotBlank String email);

	public Optional<Administrador> findByAtivoIsTrueAndNome(@NotNull @NotBlank String nome);
}
