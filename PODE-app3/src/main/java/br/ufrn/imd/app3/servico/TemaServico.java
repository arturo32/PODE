package br.ufrn.imd.app3.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.GenericoServico;
import br.ufrn.imd.pode.servico.TipoPersistencia;

import br.ufrn.imd.app3.modelo.Tema;
import br.ufrn.imd.app3.modelo.Topico;
import br.ufrn.imd.app3.modelo.dto.TemaDTO;
import br.ufrn.imd.app3.repositorio.TemaRepositorio;

@Service
public class TemaServico extends GenericoServico<Tema, TemaDTO, Long> {

	private TemaRepositorio temaRepositorio;
	private TopicoServico topicoServico;

	@Autowired
	public void setTemaRepositorio(TemaRepositorio temaRepositorio) {
		this.temaRepositorio = temaRepositorio;
	}

	@Autowired
	public void setTopicoServico(TopicoServico topicoServico) {
		this.topicoServico = topicoServico;
	}

	@Override
	public TemaDTO converterParaDTO(Tema entity) {
		return new TemaDTO(entity);
	}

	@Override
	public Tema converterParaEntidade(TemaDTO dto) {
		Tema tema = new Tema();
		if (dto.getId() != null) {
			tema = this.buscarPorId(dto.getId());
		}
		tema.setId(dto.getId());

		if (!StringUtils.isEmpty(dto.getNome())) {
			tema.setNome(dto.getNome());
		}

		if (dto.getTopicos() != null) {
			HashSet<Topico> topicos = new HashSet<>();
			for (Long idTopico : dto.getTopicos()) {
				if (idTopico == null) {
					throw new EntidadeInconsistenteException("Topico inconsistente");
				}
				try {
					topicos.add(this.topicoServico.buscarPorId(idTopico));
				} catch (EntityNotFoundException entityNotFoundException) {
					throw new EntidadeInconsistenteException("Topico inconsistente");
				}
			}
			tema.setTopicos(topicos);
		}

		return tema;
	}

	@Override
	protected void validarModoPersistencia(TipoPersistencia tipoPersistencia, TemaDTO temaDTO) {
		ErrorPersistenciaHelper.validate(tipoPersistencia, super.obterNomeModelo(), temaDTO);
	}

	@Override
	protected void validar(TemaDTO temaDTO) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		if (StringUtils.isEmpty(temaDTO.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<Tema, Long> repositorio() {
		return temaRepositorio;
	}

	public Set<Tema> buscarTemasPorNome(String nome) {
		return this.temaRepositorio.findTemasByNomeAndAtivoIsTrue(nome);
	}
}
