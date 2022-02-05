package br.ufrn.imd.app1.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import br.ufrn.imd.pode.servico.EstudanteServico;
import br.ufrn.imd.pode.servico.VinculoServico;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.PlanoCurso;

import br.ufrn.imd.app1.modelo.VinculoBTI;
import br.ufrn.imd.app1.modelo.dto.VinculoBTIDTO;
import br.ufrn.imd.app1.repositorio.VinculoBTIRepositorio;

@Service
@Transactional
public class VinculoBTIServico extends VinculoServico<VinculoBTI, VinculoBTIDTO> {

	private VinculoBTIRepositorio repositorio;
	private CursoBTIServico cursoBTIServico;
	private PlanoCursoPesServico planoCursoPesServico;
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
	public void setPlanoCursoPesServico(PlanoCursoPesServico planoCursoPesServico) {
		this.planoCursoPesServico = planoCursoPesServico;
	}

	@Autowired
	public void setEstudanteServico(EstudanteServico estudanteServico) {
		this.estudanteServico = estudanteServico;
	}

	@Override
	protected Double gerarPercentualConclusao(Long idVinculo) {
		VinculoBTI vinculo = this.buscarPorId(idVinculo);
		PlanoCurso planoCurso = vinculo.getPlanoCurso();

		Integer cargaHorariaObrigatoriaCumprida = planoCurso.getDisciplinasCursadas().stream()
				.filter(d -> vinculo.getGradeCurricular().getDisciplinasObrigatorias().contains(d))
				.map(DisciplinaCursada::getCh)
				.reduce(0, Integer::sum);

		Integer cargaHorariaOptativaCumprida = planoCurso.getDisciplinasCursadas().stream()
				.filter(d -> vinculo.getGradeCurricular().getDisciplinasOptativas().contains(d.getDisciplina()))
				.map(DisciplinaCursada::getCh)
				.reduce(0, Integer::sum);

		Integer chobm = vinculo.getGradeCurricular().getChobm();
		Integer chopm = vinculo.getGradeCurricular().getChopm();

		Integer cargaHorariaTotal = chobm + chopm;

		Double taxaObrigatorias =  (double) chobm / cargaHorariaTotal;
		Double taxaOptativas =  (double) chopm / cargaHorariaTotal;

		Double taxaObrigatoriasCumpridas = (double) cargaHorariaObrigatoriaCumprida / chobm;
		Double taxaOptativasCumpridas = (double) cargaHorariaOptativaCumprida /chopm;

		if(taxaOptativasCumpridas > 1) {
			taxaOptativasCumpridas = 1.0;
		}

		return (taxaObrigatorias*taxaObrigatoriasCumpridas + taxaOptativas*taxaOptativasCumpridas) * 100;
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
		// TODO
	}

	@Override
	protected GenericoRepositorio<VinculoBTI, Long> repositorio() {
		return repositorio;
	}
}
