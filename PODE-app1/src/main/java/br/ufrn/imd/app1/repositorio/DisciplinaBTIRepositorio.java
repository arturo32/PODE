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
			"	disciplina.id " +
			"FROM pes\n" +
			"INNER JOIN pes_disciplina_obrigatoria\n" +
			" 	ON pes.id = pes_disciplina_obrigatoria.pes_id\n" +
			"INNER JOIN disciplina\n" +
			"	ON pes_disciplina_obrigatoria.disciplina_id = disciplina.id\n" +
			"WHERE \n" +
			"	pes.id = ?2 AND \n" +
			"	disciplina.id NOT IN (\n" +
			"		SELECT\n" +
			"			disciplina_cursada.id\n" +
			"		FROM vinculo\n" +
			"		INNER JOIN planocurso AS plano_curso\n" +
			"			ON vinculo.planocurso_id = plano_curso.id\n" +
			"		INNER JOIN plano_curso_disciplina_cursada\n" +
			"			ON plano_curso.id = plano_curso_disciplina_cursada.plano_curso_id\n" +
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_cursada\n" +
			"			ON plano_curso_disciplina_cursada.disciplina_periodo_id = disciplina_periodo_cursada.id\n" +
			"		INNER JOIN disciplina AS disciplina_cursada\n" +
			"			ON disciplina_periodo_cursada.disciplina_id = disciplina.id\n" +
			"		WHERE vinculo.id = ?1\n" +
			"	)\n" +
			"ORDER BY disciplina.ch DESC;", nativeQuery = true)
	public Set<Long> findDisciplinasObrigatoriasPendentesByVinculoAndPes(@NotNull long vinculoId, @NotNull long pesId);

	@Query(value = "SELECT" +
			"	disciplina.id " +
			"FROM pes\n" +
			"INNER JOIN pes_disciplina_optativa\n" +
			" 	ON pes.id = pes_disciplina_optativa.pes_id\n" +
			"INNER JOIN disciplina\n" +
			"	ON pes_disciplina_optativa.disciplina_id = disciplina.id\n" +
			"WHERE \n" +
			"	pes.id = ?2 AND \n" +
			"	disciplina.id NOT IN (\n" +
			"		SELECT\n" +
			"			disciplina_cursada.id\n" +
			"		FROM vinculo\n" +
			"		INNER JOIN planocurso AS plano_curso\n" +
			"			ON vinculo.planocurso_id = plano_curso.id\n" +
			"		INNER JOIN plano_curso_disciplina_cursada\n" +
			"			ON plano_curso.id = plano_curso_disciplina_cursada.plano_curso_id\n" +
			"		INNER JOIN disciplinaperiodo AS disciplina_periodo_cursada\n" +
			"			ON plano_curso_disciplina_cursada.disciplina_periodo_id = disciplina_periodo_cursada.id\n" +
			"		INNER JOIN disciplina AS disciplina_cursada\n" +
			"			ON disciplina_periodo_cursada.disciplina_id = disciplina.id\n" +
			"		WHERE vinculo.id = ?1\n" +
			"	)\n" +
			"ORDER BY disciplina.ch DESC;", nativeQuery = true)
	public Set<Long> findDisciplinasOptativasPendentesByVinculoAndPes(@NotNull long vinculoId, @NotNull long pesId);
}
