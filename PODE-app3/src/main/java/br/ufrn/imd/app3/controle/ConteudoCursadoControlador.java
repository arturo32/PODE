package br.ufrn.imd.app3.controle;

import br.ufrn.imd.app3.modelo.ConteudoCursado;
import br.ufrn.imd.app3.modelo.dto.ConteudoCursadoDTO;
import br.ufrn.imd.app3.servico.ConteudoCursadoServico;
import br.ufrn.imd.pode.controle.DisciplinaCursadaControlador;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conteudos-cursados")
public class ConteudoCursadoControlador extends DisciplinaCursadaControlador<ConteudoCursado, ConteudoCursadoDTO> {

	private ConteudoCursadoServico conteudoCursadoServico;

	@Autowired
	public void setConteudoCursadoServico(ConteudoCursadoServico conteudoCursadoServico) {
		this.conteudoCursadoServico = conteudoCursadoServico;
	}

	@Override
	protected GenericoServico<ConteudoCursado, ConteudoCursadoDTO, Long> servico() {
		return this.conteudoCursadoServico;
	}
}
