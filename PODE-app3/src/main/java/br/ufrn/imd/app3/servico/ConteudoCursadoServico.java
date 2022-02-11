package br.ufrn.imd.app3.servico;

import br.ufrn.imd.app3.modelo.ConteudoCursado;
import br.ufrn.imd.app3.modelo.dto.ConteudoCursadoDTO;
import br.ufrn.imd.app3.repositorio.ConteudoCursadoRepositorio;
import br.ufrn.imd.pode.repositorio.GenericoRepositorio;
import br.ufrn.imd.pode.servico.DisciplinaCursadaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ConteudoCursadoServico extends DisciplinaCursadaServico<ConteudoCursado, ConteudoCursadoDTO> {

	private ConteudoCursadoRepositorio repositorio;

	@Autowired
	public void setRepositorio(ConteudoCursadoRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public ConteudoCursado obterDisciplinaCursada(ConteudoCursadoDTO dto) {
		// TODO
		return null;
	}

	@Override
	public ConteudoCursadoDTO converterParaDTO(ConteudoCursado entity) {
		//TODO
		return null;
	}

	@Override
	public ConteudoCursado converterParaEntidade(ConteudoCursadoDTO conteudoCursadoDTO) {
		//TODO
		return null;
	}

	@Override
	protected void validar(ConteudoCursadoDTO conteudoCursadoDTO) {
		//TODO
	}

	@Override
	protected GenericoRepositorio<ConteudoCursado, Long> repositorio() {
		return repositorio;
	}
}
