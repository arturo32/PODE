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
			"	pes.chobm AS cho,\n" +
			"	SUM(disciplinabti.ch) AS chc\n" +
			"FROM pes\n" +
			"INNER JOIN gradecurricular_disciplina_obrigatorias\n" +
			"	ON pes.id = gradecurricular_disciplina_obrigatorias.gradecurricular_id\n" +
			"INNER JOIN disciplinaperiodo\n" +
			"	ON gradecurricular_disciplina_obrigatorias.disciplina_id = disciplinaperiodo.id\n" +
			"INNER JOIN disciplinabti\n" +
			"	ON disciplinaperiodo.disciplina_id = disciplinabti.id\n" +
			"WHERE \n" +
			"	disciplinabti.id IN (\n" +
			"		SELECT\n" +
			"			disciplina_secundario.id\n" +
			"		FROM vinculobti AS vinculo_secundario\n" +
			"		INNER JOIN planocurso_disciplina_cursadas AS plano_curso_disciplina_cursada_secundario\n" +
			"			ON vinculo_secundario.planocurso_id = plano_curso_disciplina_cursada_secundario.planocurso_id\n" +
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_secundario\n" +
			"			ON plano_curso_disciplina_cursada_secundario.disciplina_id = disciplina_periodo_secundario.id\n" +
			"		INNER JOIN disciplinabti AS disciplina_secundario\n" +
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
			"	pes.chobm AS cho,\n" +
			"	SUM(disciplinabti.ch) AS chc\n" +
			"FROM pes\n" +
			"INNER JOIN gradecurricular_disciplina_optativas\n" +
			"	ON pes.id = gradecurricular_disciplina_optativas.gradecurricular_id\n" +
			"INNER JOIN disciplinabti\n" +
			"	ON gradecurricular_disciplina_optativas.disciplina_id = disciplinabti.id\n" +
			"WHERE \n" +
			"	disciplinabti.id IN (\n" +
			"		SELECT\n" +
			"			disciplina_secundario.id\n" +
			"		FROM vinculobti AS vinculo_secundario\n" +
			"		INNER JOIN planocurso_disciplina_cursadas AS plano_curso_disciplina_cursada_secundario\n" +
			"			ON vinculo_secundario.planocurso_id = plano_curso_disciplina_cursada_secundario.planocurso_id\n" +
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_secundario\n" +
			"			ON plano_curso_disciplina_cursada_secundario.disciplina_id = disciplina_periodo_secundario.id\n" +
			"		INNER JOIN disciplinabti AS disciplina_secundario\n" +
			"			ON disciplina_periodo_secundario.disciplina_id = disciplina_secundario.id\n" +
			"		WHERE vinculo_secundario.id = :vinculoId\n" +
			"	)\n" +
			"GROUP BY pes.id\n" +
			"ORDER BY pes.id ASC;\n", nativeQuery = true)
	public Set<PesChOptativaCumprida> findPesComChOptativaCumprida(@NotNull long vinculoId);
}
