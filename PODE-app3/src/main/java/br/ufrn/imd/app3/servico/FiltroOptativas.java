package br.ufrn.imd.app3.servico;

import br.ufrn.imd.app3.modelo.Enfase;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FiltroOptativas implements FiltroDisciplinaServico {

	EnfaseServico enfaseServico;

	@Autowired
	public void setEnfaseServico(EnfaseServico enfaseServico) {
		this.enfaseServico = enfaseServico;
	}

	@Override
	public String obterNome() {
		return "optativas";
	}

	@Override
	public void validar(Map<String, String> parametros) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		try {
			Long id = Long.parseLong(parametros.get("enfase_id"));
			try {
				enfaseServico.buscarPorId(id);
			} catch (Exception e) {
				exceptionHelper.add("Não foi possivel encontrar uma enfase com o id informado");
			}
		} catch (Exception e) {
			exceptionHelper.add("Não foi possivel identificar qual o id da enfase escolhida");
		}

		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	public Set<DisciplinaDTO> filtrar(Map<String, String> parametros) {
		Enfase enfase = enfaseServico.buscarPorId(Long.parseLong(parametros.get("enfase_id")));
		return enfase.getDisciplinasOptativas().stream().map(DisciplinaDTO::new).collect(Collectors.toSet());
	}
}
