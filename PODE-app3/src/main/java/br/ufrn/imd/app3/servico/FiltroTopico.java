package br.ufrn.imd.app3.servico;

import br.ufrn.imd.app3.modelo.Topico;
import br.ufrn.imd.pode.exception.ValidacaoException;
import br.ufrn.imd.pode.helper.ExceptionHelper;
import br.ufrn.imd.pode.modelo.dto.DisciplinaDTO;
import br.ufrn.imd.pode.servico.FiltroDisciplinaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FiltroTopico implements FiltroDisciplinaServico {


	private TopicoServico topicoServico;
	private ConteudoServico conteudoServico;

	@Autowired
	public void setTopicoServico(TopicoServico topicoServico){
		this.topicoServico = topicoServico;
	}

	@Autowired
	public void setConteudoServico(ConteudoServico conteudoServico) {
		this.conteudoServico = conteudoServico;
	}

	@Override
	public String obterNome() {
		return "topico";
	}

	@Override
	public void validar(Map<String, String> parametros) {
		ExceptionHelper exceptionHelper = new ExceptionHelper();
		String nome = parametros.get("nome_topico");
		if (nome.isBlank()) {
			exceptionHelper.add("Nome do tópico inconsistente");
		} else {
			Set<Topico> topicos = this.topicoServico.buscarTopicosPorNome(nome);
			if (topicos.isEmpty()) {
				exceptionHelper.add("Nenhum tópico encontrado com nome=" + nome);
			}
		}
		if (!exceptionHelper.getMessage().isEmpty()) {
			throw new ValidacaoException(exceptionHelper.getMessage());
		}
	}

	@Override
	public Set<DisciplinaDTO> filtrar(Map<String, String> parametros) {
		String nome = parametros.get("nome_topico");
		Set<Topico> topicos = this.topicoServico.buscarTopicosPorNome(nome);
		return new HashSet<>(this.conteudoServico.converterParaListaDTO(
				this.conteudoServico.buscarPorTopicos(topicos)
		));
	}

}
