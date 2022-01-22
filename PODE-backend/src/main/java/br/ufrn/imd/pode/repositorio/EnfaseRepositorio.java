package br.ufrn.imd.pode.repositorio;

import br.ufrn.imd.pode.modelo.Enfase;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnfaseRepositorio extends GenericoRepositorio<Enfase, Long> {

	public List<Enfase> findByAtivoIsTrueAndCurso_Id(Long curso_id);
	
}
