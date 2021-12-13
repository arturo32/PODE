package br.ufrn.imd.pode.repository;

import br.ufrn.imd.pode.model.Disciplina;
import br.ufrn.imd.pode.model.view.DisciplinaPendente;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Repository
public interface DisciplinaRepository extends GenericRepository<Disciplina, Long> {

	public Set<Disciplina> findDisciplinasByAtivoIsTrueAndCodigoIs(@NotNull @NotBlank String codigo);

	public Set<Disciplina> findDisciplinasByAtivoIsTrueAndCodigoIn(Collection<@NotNull @NotBlank String> codigos);

	@Query(value = "SELECT\n" + 
			"	disciplina.id,\n" + 
			"	disciplina.nome,\n" + 
			"	disciplina.codigo,\n" + 
			"	disciplina.ch,\n" + 
			"	disciplina.prerequisitos,\n" + 
			"	disciplina.corequisitos,\n" + 
			"	disciplina.equivalentes\n" + 
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
	public Set<DisciplinaPendente> findDisciplinasObrigatoriasPendentesByVinculoAndPes(@NotNull long vinculoId, @NotNull long pesId);

	@Query(value = "SELECT\n" + 
			"	disciplina.id,\n" + 
			"	disciplina.nome,\n" + 
			"	disciplina.codigo,\n" + 
			"	disciplina.ch,\n" + 
			"	disciplina.prerequisitos,\n" + 
			"	disciplina.corequisitos,\n" + 
			"	disciplina.equivalentes\n" + 
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
	public Set<DisciplinaPendente> findDisciplinasOptativasPendentesByVinculoAndPes(@NotNull long vinculoId, @NotNull long pesId);
	
}
