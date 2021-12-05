package br.ufrn.imd.pode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.ufrn.imd.pode.model.Pes;
import br.ufrn.imd.pode.model.result.PesCumprido;

public interface PesRepository extends GenericRepository<Pes, Long> {
	
	@Query(value = "SELECT\n" + 
			"	pes.id AS id,\n" + 
			"	pes.nome AS nome,\n" + 
			"	pes.chm AS chm,\n" + 
			"	pes.cho AS cho,\n" + 
			"	SUM(disciplina.ch) AS ch_cumprida\n" + 
			"FROM vinculo\n" + 
			"INNER JOIN plano_curso_pes_interesse\n" + 
			"	ON vinculo.planocurso_id = plano_curso_pes_interesse.plano_curso_id\n" + 
			"INNER JOIN pes\n" + 
			"	ON plano_curso_pes_interesse.pes_id = pes.id\n" + 
			"INNER JOIN pes_disciplina_obrigatoria\n" + 
			"	ON pes.id = pes_disciplina_obrigatoria.pes_id\n" + 
			"INNER JOIN disciplina\n" + 
			"	ON pes_disciplina_obrigatoria.disciplina_id = disciplina.id\n" + 
			"WHERE \n" + 
			"	vinculo.id = ?1\n" + 
			"	AND disciplina.id IN (\n" + 
			"		-- Buscar disciplinas cursadas\n" + 
			"		SELECT\n" + 
			"			disciplina_secundario.id\n" + 
			"		FROM vinculo AS vinculo_secundario\n" + 
			"		INNER JOIN plano_curso_disciplina_cursada AS plano_curso_disciplina_cursada_secundario\n" + 
			"			ON vinculo_secundario.planocurso_id = plano_curso_disciplina_cursada_secundario.plano_curso_id\n" + 
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_secundario\n" + 
			"			ON plano_curso_disciplina_cursada_secundario.disciplina_periodo_id = disciplina_periodo_secundario.id\n" + 
			"		INNER JOIN disciplina AS disciplina_secundario\n" + 
			"			ON disciplina_periodo_secundario.disciplina_id = disciplina_secundario.id\n" + 
			"		-- Escolher o vinculo!\n" + 
			"		WHERE vinculo_secundario.id = ?1\n" + 
			"	)\n" + 
			"GROUP BY pes.id;", nativeQuery = true)
	public List<PesCumprido> buscarChobCumpridaDosPes(long id_vinculo);
	
	@Query(value = "SELECT\n" + 
			"	pes.id AS id,\n" + 
			"	pes.nome AS nome,\n" + 
			"	pes.chm AS chm,\n" + 
			"	pes.cho AS cho,\n" + 
			"	SUM(disciplina.ch) AS cho_cumprida\n" + 
			"FROM vinculo\n" + 
			"INNER JOIN plano_curso_pes_interesse\n" + 
			"	ON vinculo.planocurso_id = plano_curso_pes_interesse.plano_curso_id\n" + 
			"INNER JOIN pes\n" + 
			"	ON plano_curso_pes_interesse.pes_id = pes.id\n" + 
			"INNER JOIN pes_disciplina_optativa\n" + 
			"	ON pes.id = pes_disciplina_optativa.pes_id\n" + 
			"INNER JOIN disciplina\n" + 
			"	ON pes_disciplina_optativa.disciplina_id = disciplina.id\n" + 
			"WHERE \n" + 
			"	vinculo.id = ?1\n" + 
			"	AND disciplina.id IN (\n" + 
			"		-- Buscar disciplinas cursadas\n" + 
			"		SELECT\n" + 
			"			disciplina_secundario.id\n" + 
			"		FROM vinculo AS vinculo_secundario\n" + 
			"		INNER JOIN plano_curso_disciplina_cursada AS plano_curso_disciplina_cursada_secundario\n" + 
			"			ON vinculo_secundario.planocurso_id = plano_curso_disciplina_cursada_secundario.plano_curso_id\n" + 
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_secundario\n" + 
			"			ON plano_curso_disciplina_cursada_secundario.disciplina_periodo_id = disciplina_periodo_secundario.id\n" + 
			"		INNER JOIN disciplina AS disciplina_secundario\n" + 
			"			ON disciplina_periodo_secundario.disciplina_id = disciplina_secundario.id\n" + 
			"		-- Escolher o vinculo!\n" + 
			"		WHERE vinculo_secundario.id = ?1\n" + 
			"	)\n" + 
			"GROUP BY pes.id;", nativeQuery = true)
	public List<PesCumprido> buscarChopCumpridaDosPes(long id_vinculo);
	
}
