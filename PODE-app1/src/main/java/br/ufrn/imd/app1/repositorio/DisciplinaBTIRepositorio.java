package br.ufrn.imd.app1.repositorio;

import br.ufrn.imd.app1.modelo.view.DisciplinaPendente;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.pode.repositorio.DisciplinaRepositorio;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;

import java.util.Set;

@Repository
public interface DisciplinaBTIRepositorio extends DisciplinaRepositorio<DisciplinaBTI> {
	@Query(value = "SELECT\n" +
			"	disciplinabti.id " +
			"FROM pes\n" +
			"INNER JOIN gradecurricular_disciplina_obrigatorias\n" +
			" 	ON pes.id = gradecurricular_disciplina_obrigatorias.gradecurricular_id\n" +
			"INNER JOIN disciplinaperiodo\n" +
			"	ON gradecurricular_disciplina_obrigatorias.disciplina_id = disciplinaperiodo.id\n" +
			"INNER JOIN disciplinabti\n" +
			"	ON disciplinaperiodo.disciplina_id = disciplinabti.id\n" +
			"WHERE \n" +
			"	pes.id = ?2 AND \n" +
			"	disciplinabti.id NOT IN (\n" +
			"		SELECT\n" +
			"			planocurso_disciplina_cursadas.disciplina_id\n" +
			"		FROM vinculobti\n" +
			"		INNER JOIN planocursopes AS plano_curso\n" +
			"			ON vinculobti.planocurso_id = plano_curso.id\n" +
			"		INNER JOIN planocurso_disciplina_cursadas\n" +
			"			ON plano_curso.id = planocurso_disciplina_cursadas.planocurso_id\n" +
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_cursada\n" +
			"			ON planocurso_disciplina_cursadas.disciplina_id = disciplina_periodo_cursada.id\n" +
			"		INNER JOIN disciplinabti AS disciplina_cursada\n" +
			"			ON disciplina_periodo_cursada.disciplina_id = disciplinabti.id\n" +
			"		WHERE vinculobti.id = ?1\n" +
			"	)\n" +
			"ORDER BY disciplinabti.ch DESC;", nativeQuery = true)
	public Set<Long> findDisciplinasObrigatoriasPendentesByVinculoAndPes(@NotNull long vinculoId, @NotNull long pesId);

	@Query(value = "SELECT" +
			"	disciplinabti.id " +
			"FROM pes\n" +
			"INNER JOIN gradecurricular_disciplina_optativas\n" +
			" 	ON pes.id = gradecurricular_disciplina_optativas.gradecurricular_id\n" +
			"INNER JOIN disciplinabti\n" +
			"	ON gradecurricular_disciplina_optativas.disciplina_id = disciplinabti.id\n" +
			"WHERE \n" +
			"	pes.id = ?2 AND \n" +
			"	disciplinabti.id NOT IN (\n" +
			"		SELECT\n" +
			"			planocurso_disciplina_cursadas.disciplina_id\n" +
			"		FROM vinculobti\n" +
			"		INNER JOIN planocursopes AS plano_curso\n" +
			"			ON vinculobti.planocurso_id = plano_curso.id\n" +
			"		INNER JOIN planocurso_disciplina_cursadas\n" +
			"			ON plano_curso.id = planocurso_disciplina_cursadas.planocurso_id\n" +
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_cursada\n" +
			"			ON planocurso_disciplina_cursadas.disciplina_id = disciplina_periodo_cursada.id\n" +
			"		INNER JOIN disciplinabti AS disciplina_cursada\n" +
			"			ON disciplina_periodo_cursada.disciplina_id = disciplinabti.id\n" +
			"		WHERE vinculobti.id = ?1\n" +
			"	)\n" +
			"ORDER BY disciplinabti.ch DESC;", nativeQuery = true)
	public Set<Long> findDisciplinasOptativasPendentesByVinculoAndPes(@NotNull long vinculoId, @NotNull long pesId);
}
