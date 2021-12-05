package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.model.PlanoCurso;
import br.ufrn.imd.pode.model.Vinculo;
import br.ufrn.imd.pode.model.dto.EnfaseDTO;
import br.ufrn.imd.pode.model.dto.VinculoDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.VinculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

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
		if (dto.getId() != null) {
			vinculo = repository.getOne(dto.getId());
			if (dto.getMatricula() != null) {
				vinculo.setMatricula(dto.getMatricula());
			}
			if (dto.getPeriodoInicial() != null) {
				vinculo.setPeriodoInicial(dto.getPeriodoInicial());
			}
			if (dto.getPeriodoAtual() != null) {
				vinculo.setPeriodoAtual(dto.getPeriodoAtual());
			}
			//Busca curso
			if(dto.getCurso() != null){
				try {
					vinculo.setCurso(this.cursoService.findById(dto.getCurso()));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("curso inconsistente");
				}
			}
			//Busca enfase
			if(dto.getEnfase() != null){
				try {
					vinculo.setEnfase(this.enfaseService.findById(dto.getEnfase()));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("enfase inconsistente");
				}
			}
			//Busca plano de curso
			if(dto.getPlanoCurso() != null){
				try {
					vinculo.setPlanoCurso(this.planoCursoService.findById(dto.getPlanoCurso()));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("planoCurso inconsistente");
				}
			}
			//Busca estudante
			if(dto.getEstudante() != null){
				try {
					vinculo.setEstudante(this.estudanteService.findById(dto.getEstudante()));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("estudante inconsistente");
				}
			}

		} else {
			vinculo.setId(dto.getId());
			vinculo.setMatricula(dto.getMatricula());
			vinculo.setPeriodoInicial(dto.getPeriodoInicial());
			vinculo.setPeriodoAtual(dto.getPeriodoAtual());
			try {
				vinculo.setCurso(this.cursoService.findById(dto.getCurso()));
			} catch (EntityNotFoundException entityNotFoundException) {
				throw new InconsistentEntityException("curso inconsistente");
			}
			vinculo.setPlanoCurso(this.planoCursoService.criarPlanoDeCursoUsandoCurso(vinculo.getCurso()));
			if(dto.getEnfase() != null){
				try {
					vinculo.setEnfase(this.enfaseService.findById(dto.getEnfase()));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new InconsistentEntityException("enfase inconsistente");
				}
				vinculo.setPlanoCurso(this.planoCursoService.criarPlanoDeCursoUsandoEnfase(vinculo.getEnfase()));
			}
			try {
				vinculo.setEstudante(this.estudanteService.findById(dto.getEstudante()));
			} catch (EntityNotFoundException entityNotFoundException) {
				throw new InconsistentEntityException("estudante inconsistente");
			}
		}

		return vinculo;
	}

	@Override
	public VinculoDTO validate(VinculoDTO dto) {
		// TODO validação
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
}
