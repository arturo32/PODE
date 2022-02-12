package br.ufrn.imd.app3.controle;

import br.ufrn.imd.app3.modelo.Tema;
import br.ufrn.imd.app3.modelo.dto.TemaDTO;
import br.ufrn.imd.app3.servico.TemaServico;
import br.ufrn.imd.pode.controle.GenericoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temas")
public class TemaControlador extends GenericoControlador<Tema, TemaDTO, Long> {

	private TemaServico temaServico;

	@Autowired
	public void setTemaServico(TemaServico temaServico) {
		this.temaServico = temaServico;
	}

	@Override
	protected GenericoServico<Tema, TemaDTO, Long> servico() {
		return temaServico;
	}
}
