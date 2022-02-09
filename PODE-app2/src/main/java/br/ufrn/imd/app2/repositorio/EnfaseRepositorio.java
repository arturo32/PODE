package br.ufrn.imd.app2.repositorio;

import br.ufrn.imd.app2.modelo.view.PesChObrigatoriaCumprida;
import br.ufrn.imd.app2.modelo.view.PesChOptativaCumprida;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.GradeCurricularRepositorio;

import br.ufrn.imd.app2.modelo.Enfase;

import java.util.Set;

@Repository
public interface EnfaseRepositorio extends GradeCurricularRepositorio<Enfase> {


}
