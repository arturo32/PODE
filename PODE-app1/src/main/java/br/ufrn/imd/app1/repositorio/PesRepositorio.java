package br.ufrn.imd.app1.repositorio;

import br.ufrn.imd.app1.modelo.view.PesChObrigatoriaCumprida;
import br.ufrn.imd.app1.modelo.view.PesChOptativaCumprida;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.GradeCurricularRepositorio;

import br.ufrn.imd.app1.modelo.Pes;

import java.util.Set;

@Repository
public interface PesRepositorio extends GradeCurricularRepositorio<Pes> {

	@Query(value = "SELECT\n" +
			"	pes.id AS id,\n" +
			"	pes.nome AS nome,\n" +
			"	pes.chm AS chm,\n" +
			"	pes.cho AS cho,\n" +
			"	SUM(disciplina.ch) AS chc\n" +
			"FROM pes\n" +
			"INNER JOIN pes_disciplina_obrigatoria\n" +
			"	ON pes.id = pes_disciplina_obrigatoria.pes_id\n" +
			"INNER JOIN disciplina\n" +
			"	ON pes_disciplina_obrigatoria.disciplina_id = disciplina.id\n" +
			"WHERE \n" +
			"	disciplina.id IN (\n" +
			"		SELECT\n" +
			"			disciplina_secundario.id\n" +
			"		FROM vinculo AS vinculo_secundario\n" +
			"		INNER JOIN plano_curso_disciplina_cursada AS plano_curso_disciplina_cursada_secundario\n" +
			"			ON vinculo_secundario.planocurso_id = plano_curso_disciplina_cursada_secundario.plano_curso_id\n" +
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_secundario\n" +
			"			ON plano_curso_disciplina_cursada_secundario.disciplina_periodo_id = disciplina_periodo_secundario.id\n" +
			"		INNER JOIN disciplina AS disciplina_secundario\n" +
			"			ON disciplina_periodo_secundario.disciplina_id = disciplina_secundario.id\n" +
			"		WHERE vinculo_secundario.id = :vinculoId\n" +
			"	)\n" +
			"GROUP BY pes.id\n" +
			"ORDER BY pes.id ASC;\n", nativeQuery = true)
	public Set<PesChObrigatoriaCumprida> findPesComChObrigatoriaCumprida(@NotNull long vinculoId);

	@Query(value = "SELECT\n" +
			"	pes.id AS id,\n" +
			"	pes.nome AS nome,\n" +
			"	pes.chm AS chm,\n" +
			"	pes.cho AS cho,\n" +
			"	SUM(disciplina.ch) AS chc\n" +
			"FROM pes\n" +
			"INNER JOIN pes_disciplina_optativa\n" +
			"	ON pes.id = pes_disciplina_optativa.pes_id\n" +
			"INNER JOIN disciplina\n" +
			"	ON pes_disciplina_optativa.disciplina_id = disciplina.id\n" +
			"WHERE \n" +
			"	disciplina.id IN (\n" +
			"		SELECT\n" +
			"			disciplina_secundario.id\n" +
			"		FROM vinculo AS vinculo_secundario\n" +
			"		INNER JOIN plano_curso_disciplina_cursada AS plano_curso_disciplina_cursada_secundario\n" +
			"			ON vinculo_secundario.planocurso_id = plano_curso_disciplina_cursada_secundario.plano_curso_id\n" +
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_secundario\n" +
			"			ON plano_curso_disciplina_cursada_secundario.disciplina_periodo_id = disciplina_periodo_secundario.id\n" +
			"		INNER JOIN disciplina AS disciplina_secundario\n" +
			"			ON disciplina_periodo_secundario.disciplina_id = disciplina_secundario.id\n" +
			"		WHERE vinculo_secundario.id = :vinculoId\n" +
			"	)\n" +
			"GROUP BY pes.id\n" +
			"ORDER BY pes.id ASC;\n", nativeQuery = true)
	public Set<PesChOptativaCumprida> findPesComChOptativaCumprida(@NotNull long vinculoId);
}
