package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.BusinessException;
import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.Vinculo;
import br.ufrn.imd.pode.model.dto.VinculoDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.VinculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class VinculoService extends GenericService<Vinculo, VinculoDTO, Long> {

	private VinculoRepository repository;
	private CursoService cursoService;
	private EnfaseService enfaseService;
	private PlanoCursoService planoCursoService;
	private EstudanteService estudanteService;

	@Override
	public VinculoDTO convertToDto(Vinculo vinculo) {
		return new VinculoDTO(vinculo);
	}

	@Override
	public Vinculo convertToEntity(VinculoDTO dto) {
		Vinculo vinculo = new Vinculo();

		//Se for uma edição
		if (dto.getId() != null) {
			vinculo = this.findById(dto.getId());
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
				vinculo.setCurso(this.cursoService.findById(dto.getIdCurso()));
			} catch (EntityNotFoundException entityNotFoundException) {
				throw new InconsistentEntityException("curso inconsistente");
			}
		}
		//Busca enfase
		if(dto.getIdEnfase() != null){
			try {
				vinculo.setEnfase(this.enfaseService.findById(dto.getIdEnfase()));
			} catch (EntityNotFoundException entityNotFoundException) {
				throw new InconsistentEntityException("enfase inconsistente");
			}
		}

		// Cria plano de curso caso necessário
		if (dto.getId() == null) {
			if (dto.getIdEnfase() == null) {
				vinculo.setPlanoCurso(planoCursoService.criarPlanoDeCursoUsandoCurso(vinculo.getCurso()));
			} else {
				vinculo.setPlanoCurso(planoCursoService.criarPlanoDeCursoUsandoEnfase(vinculo.getEnfase()));
			}
		}

		//Busca plano de curso
		if(dto.getIdPlanoCurso() != null && dto.getId() != null){
			try {
				vinculo.setPlanoCurso(this.planoCursoService.findById(dto.getIdPlanoCurso()));
			} catch (EntityNotFoundException entityNotFoundException) {
				throw new InconsistentEntityException("planoCurso inconsistente");
			}
		}

		//Busca estudante
		if(dto.getIdEstudante() != null){
			try {
				vinculo.setEstudante(this.estudanteService.findById(dto.getIdEstudante()));
			} catch (EntityNotFoundException entityNotFoundException) {
				throw new InconsistentEntityException("estudante inconsistente");
			}
		}

		return vinculo;
	}

	@Override
	public VinculoDTO validate(VinculoDTO dto) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		if (dto.getIdEstudante() != null) {
			try {
				estudanteService.findById(dto.getIdEstudante());
			} catch (EntityNotFoundException e) {
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
				cursoService.findById(dto.getIdCurso());
			} catch (EntityNotFoundException e) {
				exceptionHelper.add("curso de id: "+dto.getIdCurso()+" não encontrado");
			}
		} else {
			exceptionHelper.add("curso não foi informado");
		}

		if (dto.getIdEnfase() != null) {
			try {
				Enfase enfase = enfaseService.findById(dto.getIdEnfase());
				if (!enfase.getCurso().getId().equals(dto.getIdCurso())) {
					exceptionHelper.add("enfase de id: "+dto.getIdEnfase()+" não pertence ao curso de id: " + dto.getIdCurso());
				}
			} catch (EntityNotFoundException e) {
				exceptionHelper.add("enfase de id: "+dto.getIdEnfase()+" não encontrado");
			}
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

		return dto;
	}

	@Override
	protected GenericRepository<Vinculo, Long> repository() {
		return this.repository;
	}

	public VinculoRepository getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(VinculoRepository repository) {
		this.repository = repository;
	}

	@Autowired
	public void setCursoService(CursoService cursoService){
		this.cursoService = cursoService;
	}

	@Autowired
	public void setEnfaseService(EnfaseService enfaseService){
		this.enfaseService = enfaseService;
	}

	@Autowired
	public void setPlanoCursoService(PlanoCursoService planoCursoService){
		this.planoCursoService = planoCursoService;
	}

	@Autowired
	public void setEstudanteService(EstudanteService estudanteService) {
		this.estudanteService = estudanteService;
	}

	public Vinculo findByPlanoCursoId(Long id) {
		Optional<Vinculo> entity = repository.findByAtivoIsTrueAndPlanoCurso_Id(id);
		if (entity.isEmpty()) {
			throw new EntityNotFoundException("Entidade do tipo '" + this.getModelName()
					+ "' de id: '" + id + "' não encontrada");
		}
		return entity.get();
	}

	public Vinculo mudaEnfase(Long vinculoId, Long enfaseId) {
		Vinculo vinculo = this.findById(vinculoId);
		Enfase enfase = this.enfaseService.findById(enfaseId);
		if(vinculo.getCurso().getId().equals(enfase.getCurso().getId())) {
			vinculo.setEnfase(enfase);
			vinculo.setPlanoCurso(planoCursoService.alterarPlanoCursoEnfase(vinculo.getPlanoCurso(), enfase));
			return repository.save(vinculo);
		} else {
			throw new BusinessException("Ênfase indicada não pertence ao curso que o vínculo está associado");
		}
	}

	public Vinculo atualizaPeriodoAtual(Long vinculoId, Integer periodoNovo) {
		Vinculo vinculo = this.findById(vinculoId);
		vinculo.setPeriodoAtualAno(periodoNovo);
		return repository.save(vinculo);
	}
}
