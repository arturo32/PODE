package br.ufrn.imd.app3.servico;

import br.ufrn.imd.app3.modelo.*;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.PlanoCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import br.ufrn.imd.pode.servico.EstudanteServico;
import br.ufrn.imd.pode.servico.VinculoServico;

import br.ufrn.imd.app3.modelo.dto.VinculoBTIDTO;
import br.ufrn.imd.app3.repositorio.VinculoBTIRepositorio;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class VinculoBTIServico extends VinculoServico<VinculoBTI, VinculoBTIDTO> {

	private VinculoBTIRepositorio repositorio;
	private CursoBTIServico cursoBTIServico;
	private PlanoCursoEnfaseServico planoCursoPesServico;
	private EstudanteServico estudanteServico;

	@Autowired
	public void setRepositorio(VinculoBTIRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public VinculoRepositorio<VinculoBTI> getRepositorio() {
		return repositorio;
	}

	@Autowired
	public void setCursoBTIServico(CursoBTIServico cursoBTIServico) {
		this.cursoBTIServico = cursoBTIServico;
	}

	@Autowired
	public void setPlanoCursoPesServico(PlanoCursoEnfaseServico planoCursoPesServico) {
		this.planoCursoPesServico = planoCursoPesServico;
	}

	@Autowired
	public void setEstudanteServico(EstudanteServico estudanteServico) {
		this.estudanteServico = estudanteServico;
	}


	private Double gerarTaxaConclusaoGrade(PlanoCurso planoCurso, GradeCurricular grade){

		Set<Disciplina> disciplinasObrigatoriasGrade = grade.getDisciplinasObrigatorias().stream()
				.map(DisciplinaCursada::getDisciplina).collect(Collectors.toSet());

		Integer cargaHorariaObrigatoriaCumprida = planoCurso.getDisciplinasCursadas().stream()
				.filter(d -> disciplinasObrigatoriasGrade.contains(d.getDisciplina()))
				.map(DisciplinaCursada::getCh)
				.reduce(0, Integer::sum);

		Integer cargaHorariaOptativaCumprida = planoCurso.getDisciplinasCursadas().stream()
				.filter(d -> grade.getDisciplinasOptativas().contains(d.getDisciplina()))
				.map(DisciplinaCursada::getCh)
				.reduce(0, Integer::sum);

		Integer chobm = grade.getChobm();
		Integer chopm = grade.getChopm();

		Integer cargaHorariaTotal = chobm + chopm;

		Double taxaObrigatorias =  (double) chobm / cargaHorariaTotal;
		Double taxaOptativas =  (double) chopm / cargaHorariaTotal;

		Double taxaObrigatoriasCumpridas = (double) cargaHorariaObrigatoriaCumprida / chobm;
		Double taxaOptativasCumpridas = (double) cargaHorariaOptativaCumprida /chopm;

		if(taxaOptativasCumpridas > 1) {
			taxaOptativasCumpridas = 1.0;
		}

		return taxaObrigatorias * taxaObrigatoriasCumpridas + taxaOptativas * taxaOptativasCumpridas;
	}

	@Override
	protected Double gerarPercentualConclusao(Long idVinculo) {
		VinculoBTI vinculo = this.buscarPorId(idVinculo);
		PlanoCurso planoCurso = vinculo.getPlanoCurso();
		GradeCurricular grade = vinculo.getGradeCurricular();

		// TODO:
//		Integer chTotalPes = ((PlanoCursoEnfase) planoCurso).getGradesParalelas().stream()
//				.map(d -> d.getChopm() + d.getChobm())
//				.reduce(0, Integer::sum);
		Integer chTotalPes = 0;

		Integer chTotalCurso = grade.getChobm() + grade.getChopm();

		Integer chTotal = chTotalPes + chTotalCurso;

		// TODO:
//		Double horasCumpridasPes = ((PlanoCursoEnfase) planoCurso).getGradesParalelas().stream()
//				.map(gradePes -> {
//					Integer chTotalGradePes = gradePes.getChobm() + gradePes.getChopm();
//					return gerarTaxaConclusaoGrade(planoCurso, gradePes) * chTotalGradePes;
//				})
//				.reduce(0.0, Double::sum);
		Double horasCumpridasPes = 0.0;
		Double taxaPesCumpridas = horasCumpridasPes / chTotal;
		Double taxaCursoCumpridas = gerarTaxaConclusaoGrade(planoCurso, grade);

		Double taxaPes = (double) chTotalPes / chTotal;
		Double taxaCurso = (double) chTotalCurso / chTotal;

		return (taxaPesCumpridas * taxaPes + taxaCursoCumpridas * taxaCurso) * 100;
	}

	@Override
	public VinculoBTIDTO converterParaDTO(VinculoBTI entity) {
		return new VinculoBTIDTO(entity);
	}

	@Override
	public VinculoBTI converterParaEntidade(VinculoBTIDTO dto) {
		VinculoBTI vinculo = new VinculoBTI();

		//Se for uma edição
		if (dto.getId() != null) {
			vinculo = this.buscarPorId(dto.getId());
		}

		vinculo.setId(dto.getId());
		if (dto.getMatricula() != null) {
			vinculo.setMatricula(dto.getMatricula());
		}
		if (dto.getPeriodoInicialAno() != null) {
			vinculo.setPeriodoInicialAno(dto.getPeriodoInicialAno());
		}
		if (dto.getPeriodoInicialPeriodo() != null) {
			vinculo.setPeriodoInicialPeriodo(dto.getPeriodoInicialPeriodo());
		}
		if (dto.getPeriodoAtualAno() != null) {
			vinculo.setPeriodoAtualAno(dto.getPeriodoAtualAno());
		}
		if (dto.getPeriodoAtualPeriodo() != null) {
			vinculo.setPeriodoAtualPeriodo(dto.getPeriodoAtualPeriodo());
		}

		//Busca curso
		if(dto.getIdCurso() != null){
			try {
				vinculo.setGradeCurricular(this.cursoBTIServico.buscarPorId(dto.getIdCurso()));
			} catch (EntidadeNaoEncontradaException entityNotFoundException) {
				throw new EntidadeInconsistenteException("curso inconsistente");
			}
		}
		// Cria plano de curso caso necessário
		if (dto.getId() == null) {
			vinculo.setPlanoCurso(this.planoCursoPesServico.criarPlanoDeCursoUsandoCurso(vinculo.getGradeCurricular()));
		}

		//Busca plano de curso
		if(dto.getIdPlanoCurso() != null && dto.getId() != null){
			try {
				vinculo.setPlanoCurso(this.planoCursoPesServico.buscarPorId(dto.getIdPlanoCurso()));
			} catch (EntidadeNaoEncontradaException entityNotFoundException) {
				throw new EntidadeInconsistenteException("planoCurso inconsistente");
			}
		}

		//Busca estudante
		if(dto.getIdEstudante() != null){
			try {
				vinculo.setEstudante(this.estudanteServico.buscarPorId(dto.getIdEstudante()));
			} catch (EntidadeNaoEncontradaException entityNotFoundException) {
				throw new EntidadeInconsistenteException("estudante inconsistente");
			}
		}

		return vinculo;
	}

	@Override
	protected void validar(VinculoBTIDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		if (dto.getIdEstudante() != null) {
			try {
				estudanteServico.buscarPorId(dto.getIdEstudante());
			} catch (EntidadeNaoEncontradaException e) {
				exceptionHelper.add("estudante de id: "+dto.getIdEstudante()+" não encontrado");
			}
		} else {
			exceptionHelper.add("estudante não foi informado");
		}

		// Verifica matricula
		if (StringUtils.isEmpty(dto.getMatricula())) {
			exceptionHelper.add("matricula não foi informada");
		}

		if (dto.getIdCurso() != null) {
			try {
				cursoBTIServico.buscarPorId(dto.getIdCurso());
			} catch (EntidadeNaoEncontradaException e) {
				exceptionHelper.add("curso de id: "+dto.getIdCurso()+" não encontrado");
			}
		} else {
			exceptionHelper.add("curso não foi informado");
		}

		if (dto.getPeriodoInicialAno() == null) {
			exceptionHelper.add("ano inicial não foi informado");
		}

		if (dto.getPeriodoAtualAno() == null) {
			exceptionHelper.add("ano atual não foi informado");
		}

		if (dto.getPeriodoInicialPeriodo() != null) {
			if(dto.getPeriodoInicialPeriodo() != 1 || dto.getPeriodoInicialPeriodo() != 2) {
				exceptionHelper.add("periodo inicial invalido, deve ser 1 ou 2");
			}
		} else {
			exceptionHelper.add("periodo inicial não foi informado");
		}

		if (dto.getPeriodoAtualPeriodo() != null) {
			if(dto.getPeriodoInicialPeriodo() != 1 || dto.getPeriodoInicialPeriodo() != 2) {
				exceptionHelper.add("periodo atual invalido, deve ser 1 ou 2");
			}
		} else {
			exceptionHelper.add("periodo atual não foi informado");
		}

		// Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}

	}

	@Override
	protected GenericoRepositorio<VinculoBTI, Long> repositorio() {
		return repositorio;
	}
}
