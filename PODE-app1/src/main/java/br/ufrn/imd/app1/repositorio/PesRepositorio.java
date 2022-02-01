package br.ufrn.imd.app1.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.GradeCurricularRepositorio;

import br.ufrn.imd.app1.modelo.Pes;

@Repository
public interface PesRepositorio extends GradeCurricularRepositorio<Pes> {
}
