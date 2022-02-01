package br.ufrn.imd.app1.repositorio;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import br.ufrn.imd.app1.modelo.DisciplinaPeriodo;

@Repository
public interface DisciplinaPeriodoRepositorio extends GenericoRepositorio<DisciplinaPeriodo, Long> {
}
