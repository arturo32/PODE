package br.ufrn.imd.app3.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.GradeCurricularControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app3.modelo.Enfase;
import br.ufrn.imd.app3.modelo.dto.EnfaseDTO;
import br.ufrn.imd.app3.servico.EnfaseServico;

@RestController
@RequestMapping("/enfase")
public class EnfaseControlador extends GradeCurricularControlador<Enfase, EnfaseDTO> {

	private EnfaseServico enfaseServico;

	@Autowired
	public void setEnfaseServico(EnfaseServico enfaseServico) {
		this.enfaseServico = enfaseServico;
	}

	@Override
	protected GenericoServico<Enfase, EnfaseDTO, Long> servico() {
		return this.enfaseServico;
	}

}
