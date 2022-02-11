package br.ufrn.imd.app3.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.DisciplinaControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app3.modelo.Conteudo;
import br.ufrn.imd.app3.modelo.dto.ConteudoDTO;
import br.ufrn.imd.app3.servico.ConteudoServico;

@RestController
@RequestMapping("/conteudos")
public class ConteudoControlador extends DisciplinaControlador<Conteudo, ConteudoDTO> {

	private ConteudoServico conteudoServico;

	@Autowired
	public void setDisciplinaBTIServico(ConteudoServico conteudoServico) {
		this.conteudoServico = conteudoServico;
	}

	@Override
	protected GenericoServico<Conteudo, ConteudoDTO, Long> servico() {
		return this.conteudoServico;
	}
}
