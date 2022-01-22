package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.DisciplinaPeriodo;
import br.ufrn.imd.pode.modelo.PlanoCurso;
import br.ufrn.imd.pode.modelo.dto.DisciplinaPeriodoDTO;
import br.ufrn.imd.pode.repositorio.DisciplinaPeriodoRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DisciplinaPeriodoServico extends GenericoServico<DisciplinaPeriodo, DisciplinaPeriodoDTO, Long> {

	private DisciplinaPeriodoRepositorio repository;
	private DisciplinaServico disciplinaService;
	private PlanoCursoServico planoCursoService;

	@Override
	public DisciplinaPeriodoDTO convertToDto(DisciplinaPeriodo disciplinaPeriodo) {
		return new DisciplinaPeriodoDTO(disciplinaPeriodo);
	}

	@Override
	public DisciplinaPeriodo convertToEntity(DisciplinaPeriodoDTO dto) {
		DisciplinaPeriodo disciplinaPeriodo = new DisciplinaPeriodo();

		//Se for uma edição
		if (dto.getId() != null) {
			disciplinaPeriodo = this.findById(dto.getId());
		}

		disciplinaPeriodo.setId(dto.getId());

		if (dto.getIdDisciplina() == null) {
			throw new EntidadeInconsistenteException("disciplina inconsistente");
		}
		try {
			disciplinaPeriodo
					.setDisciplina(this.disciplinaService.findById(dto.getIdDisciplina()));
		} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException){
			throw new EntidadeInconsistenteException("disciplina inconsistente");
		}

		if (dto.getPeriodo() != null) {
			disciplinaPeriodo.setPeriodo(dto.getPeriodo());
		}

		return disciplinaPeriodo;
	}

	@Override
	protected GenericoRepositorio<DisciplinaPeriodo, Long> repository() {
		return this.repository;
	}

	public DisciplinaPeriodoRepositorio getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(DisciplinaPeriodoRepositorio disciplinaPeriodoRepository) {
		this.repository = disciplinaPeriodoRepository;
	}

	public DisciplinaServico getDisciplinaService() {
		return disciplinaService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaServico disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	public PlanoCursoServico getPlanoCursoService() {
		return planoCursoService;
	}

	@Autowired
	public void setPlanoCursoService(PlanoCursoServico planoCursoService) {
		this.planoCursoService = planoCursoService;
	}

	@Override
	public DisciplinaPeriodoDTO validate(DisciplinaPeriodoDTO disciplinaPeriodo) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplina
		if (disciplinaPeriodo.getIdDisciplina()== null || disciplinaPeriodo.getIdDisciplina() < 0) {
			exceptionHelper.add("disciplina inconsistente");
		} else {
			try {
				this.disciplinaService.findById(disciplinaPeriodo.getIdDisciplina());
			} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
				exceptionHelper.add("disciplina inexistente");
			}
		}

		//Verifica período
		if (disciplinaPeriodo.getPeriodo() == null || disciplinaPeriodo.getPeriodo() <= 0) {
			exceptionHelper.add("periodo inválido");
		}
		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return disciplinaPeriodo;
		} else {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	public List<DisciplinaPeriodo> disciplinaPendentesPlanoCurso(Long planoCursoId) {
		PlanoCurso planoCurso = planoCursoService.findById(planoCursoId);
		List<DisciplinaPeriodo> pendentes = new ArrayList<>(planoCurso.getDisciplinasPendentes());
		pendentes.sort((d1, d2) -> -d2.getPeriodo().compareTo(d1.getPeriodo()));
		return pendentes;
	}

	public List<DisciplinaPeriodo> disciplinaCursadasPlanoCurso(Long planoCursoId) {
		PlanoCurso planoCurso = planoCursoService.findById(planoCursoId);
		List<DisciplinaPeriodo> pendentes = new ArrayList<>(planoCurso.getDisciplinasCursadas());
		pendentes.sort((d1, d2) -> -d2.getPeriodo().compareTo(d1.getPeriodo()));
		return pendentes;
	}

	public DisciplinaPeriodo getDisciplinaPeriodoPorPeriodoDisciplinaId(Integer periodo, Long disciplinaId) {
		Optional<DisciplinaPeriodo> opt = repository.findByAtivoIsTrueAndPeriodoAndDisciplina_Id(periodo, disciplinaId);
		return opt.orElseGet(() -> repository.save(new DisciplinaPeriodo(disciplinaService.findById(disciplinaId), periodo)));
	}
}
