package br.ufrn.imd.app1.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PlanoCursoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaServico;
import br.ufrn.imd.pode.servico.PlanoCursoServico;

import br.ufrn.imd.app1.modelo.*;
import br.ufrn.imd.app1.modelo.dto.DisciplinaBTIDTO;
import br.ufrn.imd.app1.modelo.dto.PlanoCursoPesDTO;
import br.ufrn.imd.app1.repositorio.PlanoCursoPesRepositorio;

@Service
@Transactional
public class PlanoCursoPesServico extends PlanoCursoServico<PlanoCursoPes, PlanoCursoPesDTO> {

	private DisciplinaBTIServico disciplinaBTIServico;
	private DisciplinaPeriodoServico disciplinaPeriodoServico;
	private PesServico pesServico;

	private PlanoCursoPesRepositorio repositorio;

	@Autowired
	public void setRepositorio(PlanoCursoPesRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setDisciplinaBTIServico(DisciplinaBTIServico disciplinaBTIServico) {
		this.disciplinaBTIServico = disciplinaBTIServico;
	}

	@Autowired
	public void setDisciplinaPeriodoServico(DisciplinaPeriodoServico disciplinaPeriodoServico) {
		this.disciplinaPeriodoServico = disciplinaPeriodoServico;
	}

	@Autowired
	public void setPesServico(PesServico pesServico) {
		this.pesServico = pesServico;
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
		// TODO
		CursoBTI curso1 = (CursoBTI) curso;
		PlanoCursoPes planoCurso = new PlanoCursoPes();
		planoCurso.setDisciplinasPendentes(new HashSet<>(curso1.getDisciplinasPeriodoObrigatorias()));
		return repositorio.save(planoCurso);
	}

	@Override
	public PlanoCursoPesDTO converterParaDTO(PlanoCursoPes entity) {
		return new PlanoCursoPesDTO(entity);
	}

	@Override
	public PlanoCursoPes converterParaEntidade(PlanoCursoPesDTO dto) {
		PlanoCursoPes planoCurso = new PlanoCursoPes();

		//Se for uma edição
		if (dto.getId() != null) {
			planoCurso = this.buscarPorId(dto.getId());
		}

		planoCurso.setId(dto.getId());
		if (dto.getIdDisciplinasCursadas() != null) {
			HashSet<DisciplinaPeriodo> disciplinas = new HashSet<>();
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasCursadas()) {
				if (disciplinaPeriodoDTO == null) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}
				try {
					disciplinas.add(this.disciplinaPeriodoServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaCursada inconsistente");
				}
			}
			planoCurso.setDisciplinasCursadas(disciplinas);
		}

		if (dto.getIdDisciplinasPendentes() != null){
			HashSet<DisciplinaPeriodo> disciplinas = new HashSet<>();
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasPendentes()) {
				if (disciplinaPeriodoDTO== null) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}
				try {
					disciplinas.add(this.disciplinaPeriodoServico.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("disciplinaPendente inconsistente");
				}
			}
			planoCurso.setDisciplinasPendentes(disciplinas);
		}

		if (dto.getIdPes() != null) {
			List<Pes> pes = new ArrayList<>();
			for (Long desDTO : dto.getIdPes()) {
				if (desDTO == null) {
					throw new EntidadeInconsistenteException("pes inconsistente");
				}

				try {
					pes.add(this.pesServico.buscarPorId(desDTO));
				}
				catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("pes inconsistente");
				}
			}
			planoCurso.setGradesParalelas(pes);
		}

		return planoCurso;
	}

	@Override
	protected void validar(PlanoCursoPesDTO dto) {
		// TODO
	}

	@Override
	protected GenericoRepositorio<PlanoCursoPes, Long> repositorio() {
		return repositorio;
	}
}
