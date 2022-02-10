package br.ufrn.imd.app2.servico;

import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FiltroIndefinido implements FiltroDisciplinaServico {

	@Override
	public String obterNome() {
		return "filtro";
	}

	@Override
	public void validar(Map<String, String> parametros) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		try {
			// TODO
			Long id = Long.parseLong(parametros.get("pes_id"));
			try {
				// TODO
			} catch (EntidadeNaoEncontradaException e) {
				exceptionHelper.add(e.getMessage());
			}
		} catch (NumberFormatException e) {
			exceptionHelper.add(e.getMessage());
		}
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	public Set<DisciplinaDTO> filtrar(Map<String, String> parametros) {
		Long pes_id = Long.parseLong(parametros.get("pes_id"));
		// TODO
		HashSet<DisciplinaDTO> result = new HashSet<>();
		return result;
	}
}
