package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.dto.PlanoCursoPesDTO;
import br.ufrn.imd.pode.modelo.DisciplinaInterface;
import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.ModeloAbstrato;
import br.ufrn.imd.pode.modelo.PlanoCurso;
import br.ufrn.imd.pode.modelo.dto.AbstratoDTO;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;
import br.ufrn.imd.pode.servico.PlanoCursoServico;

import javax.validation.constraints.NotNull;

public class PlanoCursoPesServico extends PlanoCursoServico<PlanoCurso, PlanoCursoPesDTO> {

	@Override
	public PlanoCursoRepositorio<PlanoCurso> getPlanoCursoRepositorio() {
		return null;
	}

	@Override
	public PlanoCurso criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso) {
		return null;
	}

	@Override
	public PlanoCursoPesDTO converterParaDTO(PlanoCurso entity) {
		return null;
	}

	@Override
	public PlanoCurso converterParaEntidade(PlanoCursoPesDTO dto) {
		return null;
	}

	@Override
	protected void validar(PlanoCursoPesDTO dto) {

	}

	@Override
	protected GenericoRepositorio repositorio() {
		return null;
	}
}
