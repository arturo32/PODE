package br.ufrn.imd.app3.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.VinculoRepositorio;
import br.ufrn.imd.pode.servico.EstudanteServico;
import br.ufrn.imd.pode.servico.VinculoServico;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Disciplina;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.GradeCurricular;
import br.ufrn.imd.pode.modelo.PlanoCurso;

import br.ufrn.imd.app3.modelo.dto.VinculoPlataformaDTO;
import br.ufrn.imd.app3.repositorio.VinculoPlataformaRepositorio;
import org.springframework.util.StringUtils;
import br.ufrn.imd.app3.modelo.*;

@Service
@Transactional
public class VinculoPlataformaServico extends VinculoServico<VinculoPlataforma, VinculoPlataformaDTO> {

	private VinculoPlataformaRepositorio repositorio;
	private CursoServico cursoServico;
	private PlanoCursoTemaServico planoCursoPesServico;
	private EstudanteServico estudanteServico;
	private TemaServico temaServico;

	@Autowired
	public void setRepositorio(VinculoPlataformaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public VinculoRepositorio<VinculoPlataforma> getRepositorio() {
		return repositorio;
	}

	@Autowired
	public void setTemaServico(TemaServico temaServico) {
		this.temaServico = temaServico;
	}

	@Autowired
	public void setCursoBTIServico(CursoServico cursoServico) {
		this.cursoServico = cursoServico;
	}

	@Autowired
	public void setPlanoCursoPesServico(PlanoCursoTemaServico planoCursoPesServico) {
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
		Double taxaOptativasCumpridas = (double) cargaHorariaOptativaCumprida / chopm;

		if(taxaOptativasCumpridas > 1) {
			taxaOptativasCumpridas = 1.0;
		}

		return taxaObrigatorias * taxaObrigatoriasCumpridas + taxaOptativas * taxaOptativasCumpridas;
	}

	@Override
	protected Double gerarPercentualConclusao(Long idVinculo) {
		VinculoPlataforma vinculo = this.buscarPorId(idVinculo);
		PlanoCurso planoCurso = vinculo.getPlanoCurso();
		GradeCurricular grade = vinculo.getGradeCurricular();

		Double taxaCursoCumpridas = gerarTaxaConclusaoGrade(planoCurso, grade);
		return taxaCursoCumpridas * 100;
	}

	@Override
	public VinculoPlataformaDTO converterParaDTO(VinculoPlataforma entity) {
		return new VinculoPlataformaDTO(entity);
	}

	@Override
	public VinculoPlataforma converterParaEntidade(VinculoPlataformaDTO dto) {
		VinculoPlataforma vinculo = new VinculoPlataforma();

		//Se for uma edição
		if (dto.getId() != null) {
			vinculo = this.buscarPorId(dto.getId());
		}

		vinculo.setId(dto.getId());
		if (dto.getMatricula() != null) {
			vinculo.setMatricula(dto.getMatricula());
		}

		//Busca curso
		if(dto.getIdCurso() != null){
			try {
				vinculo.setGradeCurricular(this.cursoServico.buscarPorId(dto.getIdCurso()));
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

		if (dto.getTemasInteresse() != null) {
			HashSet<Tema> temas = new HashSet<>();
			for (Long temaId: dto.getTemasInteresse()) {
				if (temaId == null) {
					throw new EntidadeInconsistenteException("tema inconsistente");
				}
				try {
					temas.add(this.temaServico.buscarPorId(temaId));
				} catch (EntidadeNaoEncontradaException entityNotFoundException) {
					throw new EntidadeInconsistenteException("tema inconsistente");
				}
			}
			vinculo.setTemasInteresse(temas);
		}

		return vinculo;
	}

	@Override
	protected void validar(VinculoPlataformaDTO dto) {
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
				cursoServico.buscarPorId(dto.getIdCurso());
			} catch (EntidadeNaoEncontradaException e) {
				exceptionHelper.add("curso de id: "+dto.getIdCurso()+" não encontrado");
			}
		} else {
			exceptionHelper.add("curso não foi informado");
		}

		if (dto.getTemasInteresse() != null) {
			for (Long temaId : dto.getTemasInteresse()) {
				if (temaId== null || temaId < 0) {
					exceptionHelper.add("tema inconsistente");
				} else {
					try {
						this.temaServico.buscarPorId(temaId);
					} catch (EntidadeNaoEncontradaException entityNotFoundException) {
						exceptionHelper.add("tema(id=" + temaId + ") inexistente");
					}
				}
			}
		}

		// Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}

	}

	@Override
	protected GenericoRepositorio<VinculoPlataforma, Long> repositorio() {
		return repositorio;
	}
}
