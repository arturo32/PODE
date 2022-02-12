package br.ufrn.imd.app3.servico;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class FiltroSemNome implements FiltroDisciplinaServico {

	@Override
	public String obterNome() {
		return "sem_nome";
	}

	@Override
	public void validar(Map<String, String> parametros) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		try {
			Long id = Long.parseLong(parametros.get("???"));
			try {
				// TODO
			} catch (Exception e) {
				exceptionHelper.add("???");
			}
		} catch (Exception e) {
			exceptionHelper.add("???");
		}

		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	public Set<DisciplinaDTO> filtrar(Map<String, String> parametros) {
		//TODO
		return null;
	}
}
