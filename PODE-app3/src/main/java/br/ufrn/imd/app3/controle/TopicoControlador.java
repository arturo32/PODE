package br.ufrn.imd.app3.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.GenericoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app3.modelo.Topico;
import br.ufrn.imd.app3.modelo.dto.TopicoDTO;
import br.ufrn.imd.app3.servico.TopicoServico;

@RestController
@RequestMapping("/topicos")
public class TopicoControlador extends GenericoControlador<Topico, TopicoDTO, Long> {

	private TopicoServico topicoServico;

	@Autowired
	public void setTopicoServico(TopicoServico topicoServico) {
		this.topicoServico = topicoServico;
	}

	@Override
	protected GenericoServico<Topico, TopicoDTO, Long> servico() {
		return topicoServico;
	}
}
