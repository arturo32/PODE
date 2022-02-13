package br.ufrn.imd.app3.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaCursadaServico;

import br.ufrn.imd.app3.modelo.ConteudoCursado;
import br.ufrn.imd.app3.modelo.dto.ConteudoCursadoDTO;
import br.ufrn.imd.app3.repositorio.ConteudoCursadoRepositorio;

@Service
@Transactional
public class ConteudoCursadoServico extends DisciplinaCursadaServico<ConteudoCursado, ConteudoCursadoDTO> {

	private ConteudoServico conteudoServico;

	private ConteudoCursadoRepositorio repositorio;

	@Autowired
	public void setConteudoServico(ConteudoServico conteudoServico) {
		this.conteudoServico = conteudoServico;
	}

	@Autowired
	public void setRepositorio(ConteudoCursadoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public ConteudoCursado obterDisciplinaCursada(ConteudoCursadoDTO dto) {
		Optional<ConteudoCursado> opt = repositorio.findByAtivoIsTrueAndLocalDateAndDisciplina_Id(dto.getLocalDate(), dto.getDisciplinaId());
		return opt.orElseGet(() -> repositorio.save(new ConteudoCursado(conteudoServico.buscarPorId(dto.getDisciplinaId()), dto.getLocalDate())));
	}

	@Override
	public ConteudoCursadoDTO converterParaDTO(ConteudoCursado entity) {
		return new ConteudoCursadoDTO(entity);
	}

	@Override
	public ConteudoCursado converterParaEntidade(ConteudoCursadoDTO dto) {
		ConteudoCursado conteudoCursado = new ConteudoCursado();

		//Se for uma edição
		if (dto.getId() != null) {
			conteudoCursado = this.buscarPorId(dto.getId());
		}

		conteudoCursado.setId(dto.getId());

		if (dto.getDisciplinaId() == null) {
			throw new EntidadeInconsistenteException("conteudo inconsistente");
		}
		try {
			conteudoCursado
					.setDisciplina(this.conteudoServico.buscarPorId(dto.getDisciplinaId()));
		} catch (EntidadeNaoEncontradaException entityNotFoundException){
			throw new EntidadeInconsistenteException("conteudo inconsistente");
		}

		if (dto.getLocalDate() != null) {
			conteudoCursado.setLocalDate(dto.getLocalDate());
		}

		return conteudoCursado;
	}

	@Override
	protected void validar(ConteudoCursadoDTO conteudoCursadoDTO) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		//Verifica disciplina
		if (conteudoCursadoDTO.getDisciplinaId()== null || conteudoCursadoDTO.getDisciplinaId() < 0) {
			exceptionHelper.add("disciplina inconsistente");
		} else {
			try {
				this.conteudoServico.buscarPorId(conteudoCursadoDTO.getDisciplinaId());
			} catch (EntityNotFoundException entityNotFoundException) {
				exceptionHelper.add("disciplina inexistente");
			}
		}

		//Verifica período
		if (conteudoCursadoDTO.getLocalDate() == null) {
			exceptionHelper.add("periodo inválido");
		}
		//Verifica se existe exceção
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<ConteudoCursado, Long> repositorio() {
		return repositorio;
	}
}
