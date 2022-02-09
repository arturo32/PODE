package br.ufrn.imd.app1.servico;

import br.ufrn.imd.app1.modelo.Pes;
import br.ufrn.imd.pode.exception.EntidadeNaoEncontradaException;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.DisciplinaCursada;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FiltroPes implements FiltroDisciplinaServico {

	private PesServico pesServico;

	@Autowired
	public void setPesServico(PesServico pesServico) {
		this.pesServico = pesServico;
	}

	@Override
	public String obterNome() {
		return "pes";
	}

	@Override
	public void validar(Map<String, String> parametros) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		try {
			Long id = Long.parseLong(parametros.get("pes_id"));
			try {
				pesServico.buscarPorId(id);
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
		Pes pes = pesServico.buscarPorId(pes_id);
		HashSet<DisciplinaDTO> result = new HashSet<>();
		result.addAll(pes.getDisciplinasObrigatorias().stream().map(DisciplinaCursada::getDisciplina)
				.map(DisciplinaDTO::new).collect(Collectors.toSet()));
		result.addAll(pes.getDisciplinasOptativas().stream().map(DisciplinaDTO::new).collect(Collectors.toSet()));
		return result;
	}
}
