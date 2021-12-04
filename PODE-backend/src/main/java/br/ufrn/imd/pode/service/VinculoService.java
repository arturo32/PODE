package br.ufrn.imd.pode.service;

import br.ufrn.imd.pode.exception.EntityNotFoundException;
import br.ufrn.imd.pode.exception.InconsistentEntityException;
import br.ufrn.imd.pode.model.Vinculo;
import br.ufrn.imd.pode.model.dto.EnfaseDTO;
import br.ufrn.imd.pode.model.dto.VinculoDTO;
import br.ufrn.imd.pode.repository.GenericRepository;
import br.ufrn.imd.pode.repository.VinculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		vinculo.setId(dto.getId());
		vinculo.setMatricula(dto.getMatricula());
		vinculo.setPeriodoInicial(dto.getPeriodoInicial());
		vinculo.setPeriodoAtual(dto.getPeriodoAtual());

		//Busca curso
		if(vinculo.getCurso().getId() == null){
			throw new InconsistentEntityException("curso inconsistente");
		}
		try {
			vinculo.setCurso(this.cursoService.findById(vinculo.getCurso().getId()));
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new InconsistentEntityException("curso inconsistente");
		}

		//Busca enfases
		for(EnfaseDTO enfaseDTO : dto.getEnfases()) {
			if(enfaseDTO.getId() == null){
				throw new InconsistentEntityException("enfases inconsistentes");
			}
			try {
				vinculo.getEnfases()
						.add(this.enfaseService.findById(enfaseDTO.getId()));
			} catch (EntityNotFoundException entityNotFoundException) {
				throw new InconsistentEntityException("enfases inconsistentes");
			}
		}

		//Busca plano de curso
		if(vinculo.getPlanoCurso().getId() == null){
			throw new InconsistentEntityException("planoCurso inconsistente");
		}
		try {
			vinculo.setPlanoCurso(this.planoCursoService.findById(vinculo.getPlanoCurso().getId()));
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new InconsistentEntityException("planoCurso inconsistente");
		}

		//Busca estudante
		if(vinculo.getEstudante().getId() == null){
			throw new InconsistentEntityException("estudante inconsistente");
		}
		try {
			vinculo.setEstudante(this.estudanteService.findById(vinculo.getEstudante().getId()));
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new InconsistentEntityException("estudante inconsistente");
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
