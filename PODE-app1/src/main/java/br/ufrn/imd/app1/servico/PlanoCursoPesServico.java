package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.DisciplinaBTI;
import br.ufrn.imd.app1.modelo.PlanoCursoPes;
import br.ufrn.imd.app1.modelo.VinculoBTI;
import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;
import br.ufrn.imd.app1.modelo.dto.PlanoCursoPesDTO;
import br.ufrn.imd.app1.repositorio.DisciplinaBTIRepositorio;
import br.ufrn.imd.app1.repositorio.PlanoCursoPesRepositorio;
import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.Vinculo;
import br.ufrn.imd.pode.modelo.dto.VinculoDTO;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import br.ufrn.imd.pode.servico.PlanoCursoServico;
import br.ufrn.imd.pode.servico.VinculoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Service
@Transactional
public class PlanoCursoPesServico extends PlanoCursoServico<PlanoCursoPes, PlanoCursoPesDTO> {

	private DisciplinaBTIServico disciplinaBTIServico;

	private PlanoCursoPesRepositorio repositorio;

	@Autowired
	public void setRepositorio(PlanoCursoPesRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Override
	public DisciplinaServico<DisciplinaBTI, DisciplinaBTIDTO> getDisciplinaServico() {
		return disciplinaBTIServico;
	}

	@Override
	public PlanoCursoRepositorio<PlanoCursoPes> getPlanoCursoRepositorio() {
		return repositorio;
	}

	@Override
	public PlanoCursoPes criarPlanoDeCursoUsandoCurso(@NotNull GradeCurricular curso) {
		return null;
	}

	@Override
	public PlanoCursoPesDTO converterParaDTO(PlanoCursoPes entity) {
		return null;
	}

	@Override
	public PlanoCursoPes converterParaEntidade(PlanoCursoPesDTO dto) {
		return null;
	}

	@Override
	protected void validar(PlanoCursoPesDTO dto) {

	}

	@Override
	protected GenericoRepositorio<PlanoCursoPes, Long> repositorio() {
		return repositorio;
	}
}
