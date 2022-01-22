package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Enfase;
import br.ufrn.imd.pode.modelo.dto.EnfaseDTO;
import br.ufrn.imd.pode.repositorio.EnfaseRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class EnfaseServico extends GenericoServico<Enfase, EnfaseDTO, Long> {

	private EnfaseRepositorio repository;
	private CursoServico cursoService;
	private DisciplinaPeriodoServico disciplinaPeriodoService;

	@Override
	public EnfaseDTO converterParaDTO(Enfase enfase) {
		return new EnfaseDTO(enfase);
	}

	@Override
	public Enfase converterParaEntidade(EnfaseDTO dto) {
		Enfase enfase = new Enfase();

		//Se for uma edição
		if (dto.getId() != null) {
			enfase = this.buscarPorId(dto.getId());
		}

		enfase.setId(dto.getId());
		if(dto.getNome() != null){
			enfase.setNome(dto.getNome());
		}

		if (dto.getIdCurso() == null) {
			throw new EntidadeInconsistenteException("curso inconsistente");
		}
		try {
			enfase.setCurso(this.cursoService.buscarPorId(dto.getIdCurso()));
		} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException){
			throw new EntidadeInconsistenteException("curso inconsistente");
		}

		if (dto.getIdDisciplinasObrigatorias() != null) {
			enfase.setDisciplinasObrigatorias(new HashSet<>());
			for (Long disciplinaPeriodoDTO : dto.getIdDisciplinasObrigatorias()) {
				if (disciplinaPeriodoDTO== null) {
					throw new EntidadeInconsistenteException("disciplinaPeriodo inconsistente");
				}

				try {
					enfase.getDisciplinasObrigatorias()
							.add(this.disciplinaPeriodoService.buscarPorId(disciplinaPeriodoDTO));
				} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException){
					throw new EntidadeInconsistenteException("disciplinaPeriodo inconsistente");
				}
			}
		}

		return enfase;
	}

	@Override
	protected GenericoRepositorio<Enfase, Long> repositorio() {
		return this.repository;
	}

	public EnfaseRepositorio getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(EnfaseRepositorio enfaseRepository) {
		this.repository = enfaseRepository;
	}

	public CursoServico getCursoService() {
		return this.cursoService;
	}

	@Autowired
	public void setCursoService(CursoServico cursoService) {
		this.cursoService = cursoService;
	}

	public DisciplinaPeriodoServico getDisciplinaPeriodoService() {
		return this.disciplinaPeriodoService;
	}

	@Autowired
	public void setDisciplinaPeriodoService(DisciplinaPeriodoServico disciplinaPeriodoService) {
		this.disciplinaPeriodoService = disciplinaPeriodoService;
	}

	@Override
	public EnfaseDTO validar(EnfaseDTO enfase) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica nome
		if (StringUtils.isEmpty(enfase.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		//Verifica curso
		if (enfase.getIdCurso() == null || enfase.getIdCurso() < 0) {
			exceptionHelper.add("curso inconsistente");
		} else {
			try {
				this.cursoService.buscarPorId(enfase.getIdCurso());
			} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
				exceptionHelper.add("curso inexistente");
			}
		}

		//Verifica disciplinas obrigatórias
		if (enfase.getIdDisciplinasObrigatorias() != null) {
			for (Long disciplinaPeriodo : enfase.getIdDisciplinasObrigatorias()) {
				if (disciplinaPeriodo == null || disciplinaPeriodo < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.disciplinaPeriodoService.buscarPorId(disciplinaPeriodo);
					} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
						exceptionHelper.add("disciplinaObrigatoria(id=" + disciplinaPeriodo + ") inexistente");
					}
				}
			}
		}

		//Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return enfase;
		} else {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	public List<Enfase> findEnfasePorCurso(Long cursoId) {
		return repository.findByAtivoIsTrueAndCurso_Id(cursoId);
	}

}
