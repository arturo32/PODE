package br.ufrn.imd.app1.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.GradeCurricularControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.Pes;
import br.ufrn.imd.app1.modelo.dto.PesDTO;
import br.ufrn.imd.app1.servico.PesServico;

@RestController
@RequestMapping("/pes")
public class PesControlador extends GradeCurricularControlador<Pes, PesDTO> {

	private PesServico pesServico;

	@Autowired
	public void setPesServico(PesServico pesServico) {
		this.pesServico = pesServico;
	}

	@Override
	protected GenericoServico<Pes, PesDTO, Long> servico() {
		return this.pesServico;
	}

}
