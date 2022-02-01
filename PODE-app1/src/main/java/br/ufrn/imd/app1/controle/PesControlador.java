package br.ufrn.imd.app1.controle;

import br.ufrn.imd.app1.modelo.Pes;
import br.ufrn.imd.app1.modelo.dto.PesDTO;
import br.ufrn.imd.app1.servico.PesServico;
import br.ufrn.imd.pode.controle.GenericoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pes")
public class PesControlador extends GenericoControlador<Pes, PesDTO, Long> {

	private PesServico servico;

	@Autowired
	public void setServico(PesServico servico) {
		this.servico = servico;
	}

	@Override
	protected GenericoServico<Pes, PesDTO, Long> servico() {
		return servico;
	}
}
