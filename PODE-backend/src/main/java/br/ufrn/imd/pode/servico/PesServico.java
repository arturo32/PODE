package br.ufrn.imd.pode.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.Pes;
import br.ufrn.imd.pode.modelo.dto.PesDTO;
import br.ufrn.imd.pode.modelo.view.PesChObrigatoriaCumprida;
import br.ufrn.imd.pode.modelo.view.PesChOptativaCumprida;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.repositorio.PesRepositorio;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class PesServico extends GenericoServico<Pes, PesDTO, Long> {

	private PesRepositorio repository;
	private DisciplinaServico disciplinaService;

	@Override
	public PesDTO convertToDto(Pes pes) {
		return new PesDTO(pes);
	}

	@Override
	public Pes convertToEntity(PesDTO dto) {
		Pes pes = new Pes();

		// Se for uma edição
		if (dto.getId() != null) {
			pes = this.findById(dto.getId());
		}

		pes.setId(dto.getId());

		if (dto.getNome() != null) {
			pes.setNome(dto.getNome());
		}
		if (dto.getChm() != null) {
			pes.setChm(dto.getChm());
		}
		if (dto.getCho() != null) {
			pes.setCho(dto.getCho());
		}

		if (dto.getIdDisciplinasObrigatorias() != null) {
			pes.setDisciplinasObrigatorias(new HashSet<>());
			for (Long disciplinaDTO : dto.getIdDisciplinasObrigatorias()) {
				if (disciplinaDTO == null) {
					throw new EntidadeInconsistenteException("disciplinaObrigatoria inconsistente");
				}

				try {
					pes.getDisciplinasObrigatorias().add(this.disciplinaService.findById(disciplinaDTO));
				} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
					throw new EntidadeInconsistenteException("disciplinaObrigatoria inconsistente");
				}
			}
		}

		if (dto.getIdDisciplinasOptativas() != null) {
			pes.setDisciplinasOptativas(new HashSet<>());
			for (Long disciplinaDTO : dto.getIdDisciplinasOptativas()) {
				if (disciplinaDTO == null) {
					throw new EntidadeInconsistenteException("disciplinaOptativa inconsistente");
				}
				try {
					pes.getDisciplinasOptativas().add(this.disciplinaService.findById(disciplinaDTO));
				} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
					throw new EntidadeInconsistenteException("disciplinaOptativa inconsistente");
				}
			}
		}

		return pes;
	}

	@Override
	protected GenericoRepositorio<Pes, Long> repository() {
		return this.repository;
	}

	public PesRepositorio getRepository() {
		return this.repository;
	}

	@Autowired
	public void setRepository(PesRepositorio pesRepository) {
		this.repository = pesRepository;
	}

	public DisciplinaServico getDisciplinaService() {
		return this.disciplinaService;
	}

	@Autowired
	public void setDisciplinaService(DisciplinaServico disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	@Override
	public PesDTO validate(PesDTO pes) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		// Verifica nome
		if (StringUtils.isEmpty(pes.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		// Verifica chm
		boolean statusChm = false;
		if (pes.getChm() == null || pes.getChm() <= 0) {
			exceptionHelper.add("chm inválido");
		} else {
			statusChm = true;
		}

		// Verifica cho
		boolean statusCho = false;
		if (pes.getCho() == null || pes.getCho() <= 0) {
			exceptionHelper.add("cho inválido");
		} else {
			statusCho = true;
		}

		// Verifica cho em relação a chm
		if (statusCho && statusChm) {
			if (pes.getCho() > pes.getChm()) {
				exceptionHelper.add("impossível cho ser maior do que chm");
			}
		}

		// Verifica disciplinasObrigatorias
		if (pes.getIdDisciplinasObrigatorias() != null) {
			for (Long disciplina : pes.getIdDisciplinasObrigatorias()) {
				if (disciplina == null || disciplina < 0) {
					exceptionHelper.add("disciplinaObrigatoria inconsistente");
				} else {
					try {
						this.disciplinaService.findById(disciplina);
					} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
						exceptionHelper.add("disciplinaObrigatoria(id=" + disciplina + ") inexistente");
					}
				}
			}
		}

		// Verifica disciplinasOptativas
		if (pes.getIdDisciplinasOptativas() != null) {
			for (Long disciplina : pes.getIdDisciplinasOptativas()) {
				if (disciplina == null || disciplina < 0) {
					exceptionHelper.add("disciplinaOptativa inconsistente");
				} else {
					try {
						this.disciplinaService.findById(disciplina);
					} catch (EntidadeNaoEncontradaException entidadeNaoEncontradaException) {
						exceptionHelper.add("disciplinaOptativa(id=" + disciplina + ") inexistente");
					}
				}
			}
		}

		// Verifica se existe exceção
		if (exceptionHelper.getMessage().isEmpty()) {
			return pes;
		} else {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	public Set<PesChObrigatoriaCumprida> obterPesComChObrigatoriaCumprida(long vinculoId) {
		return this.getRepository().findPesComChObrigatoriaCumprida(vinculoId);
	}

	public Set<PesChOptativaCumprida> obterPesComChOptativaCumprida(long vinculoId) {
		return this.getRepository().findPesComChOptativaCumprida(vinculoId);
	}

}
