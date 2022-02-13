package br.ufrn.imd.app3.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;

import br.ufrn.imd.app3.modelo.Tema;

@Service
public class FiltroTema implements FiltroDisciplinaServico {

	private TemaServico temaServico;
	private ConteudoServico conteudoServico;

	@Autowired
	public void setTemaServico(TemaServico temaServico) {
		this.temaServico = temaServico;
	}

	@Autowired
	public void setConteudoServico(ConteudoServico conteudoServico) {
		this.conteudoServico = conteudoServico;
	}

	@Override
	public String obterNome() {
		return "tema";
	}

	@Override
	public void validar(Map<String, String> parametros) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		String nome = parametros.get("nome_tema");
		if (nome.isBlank()) {
			exceptionHelper.add("Nome de tema inconsistente");
		} else {
			Set<Tema> temas = this.temaServico.buscarTemasPorNome(nome);
			if (temas.isEmpty()) {
				exceptionHelper.add("Nenhum tema encontrado com nome=" + nome);
			}
		}
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	public Set<DisciplinaDTO> filtrar(Map<String, String> parametros) {
		String nome = parametros.get("nome_tema");
		Set<Tema> temas = this.temaServico.buscarTemasPorNome(nome);
		return new HashSet<>(this.conteudoServico.converterParaListaDTO(
				this.conteudoServico.buscarPorTemas(temas)
		));
	}
}
