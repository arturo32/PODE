package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.model.Enfase;
import br.ufrn.imd.pode.model.Vinculo;
import br.ufrn.imd.pode.model.dto.VinculoDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.VinculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO validação
		// TODO: verificar se enfase é válida para o curso indicado
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
		//TODO checar se enfase é válida para o curso indicado no vinculo
		Vinculo vinculo = this.findById(vinculoId);
		Enfase enfase = this.enfaseService.findById(enfaseId);
		vinculo.setEnfase(enfase);
		vinculo.setPlanoCurso(planoCursoService.alterarPlanoCursoEnfase(vinculo.getPlanoCurso(), enfase));
		return repository.save(vinculo);
	}

	public Vinculo atualizaPeriodoAtual(Long vinculoId, Integer periodoNovo) {
		Vinculo vinculo = this.findById(vinculoId);
		vinculo.setPeriodoAtualAno(periodoNovo);
		return repository.save(vinculo);
	}
}
