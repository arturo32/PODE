package br.ufrn.imd.app3.servico;

import br.ufrn.imd.app3.modelo.Topico;
import br.ufrn.imd.app3.modelo.dto.TopicoDTO;
import br.ufrn.imd.app3.repositorio.TopicoRepositorio;
import br.ufrn.imd.pode.exception.EntidadeInconsistenteException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ErrorPersistenciaHelper;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.GenericoServico;
import br.ufrn.imd.pode.servico.TipoPersistencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TopicoServico extends GenericoServico<Topico, TopicoDTO, Long> {

	private TopicoRepositorio repositorio;

	private TemaServico temaServico;

	@Autowired
	public void setRepositorio(TopicoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Autowired
	public void setTemaServico(TemaServico temaServico) {
		this.temaServico = temaServico;
	}

	@Override
	public TopicoDTO converterParaDTO(Topico entity) {
		return new TopicoDTO(entity);
	}

	@Override
	public Topico converterParaEntidade(TopicoDTO dto) {
		Topico topico = new Topico();
		if (dto.getId() != null) {
			topico = this.buscarPorId(dto.getId());
		}

		topico.setId(dto.getId());
		if (StringUtils.isEmpty(dto.getNome())) {
			topico.setNome(dto.getNome());
		}

		if (dto.getTema() != null) {
			try {
				topico.setTema(temaServico.buscarPorId(dto.getTema()));
			} catch (Exception e) {
				throw new EntidadeInconsistenteException("tema de id="+dto.getTema()+" não encontrado");
			}
		}
		return topico;
	}

	@Override
	protected void validarModoPersistencia(TipoPersistencia tipoPersistencia, TopicoDTO topicoDTO) {
		ErrorPersistenciaHelper.validate(tipoPersistencia, super.obterNomeModelo(), topicoDTO);
	}

	@Override
	protected void validar(TopicoDTO topicoDTO) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();

		if (StringUtils.isEmpty(topicoDTO.getNome())) {
			exceptionHelper.add("nome inválido");
		}

		if (topicoDTO.getTema() == null) {
			exceptionHelper.add("tema inválido");
		}

		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	protected GenericoRepositorio<Topico, Long> repositorio() {
		return repositorio;
	}
}
