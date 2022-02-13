package br.ufrn.imd.app2.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.GradeCurricularRepositorio;

import br.ufrn.imd.app2.modelo.CursoBTI;

@Repository
public interface CursoBTIRepositorio extends GradeCurricularRepositorio<CursoBTI> {
}