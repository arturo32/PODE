package br.ufrn.imd.app1.controle;

import br.ufrn.imd.pode.controle.GenericoControlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.VinculoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app1.modelo.VinculoBTI;
import br.ufrn.imd.app1.modelo.dto.VinculoBTIDTO;
import br.ufrn.imd.app1.servico.VinculoBTIServico;

@RestController
@RequestMapping("/vinculos")
public class VinculoBTIControlador extends GenericoControlador<VinculoBTI, VinculoBTIDTO, Long> {

	private VinculoBTIServico vinculoBTIServico;

	@Autowired
	public void setVinculoBTIServico(VinculoBTIServico vinculoBTIServico) {
		this.vinculoBTIServico = vinculoBTIServico;
	}

	@Override
	protected GenericoServico<VinculoBTI, VinculoBTIDTO, Long> servico() {
		return this.vinculoBTIServico;
	}

	@GetMapping({"/{idVinculo}/percentual-conclusao"})
	public Double obterPercentualConclusao(@PathVariable Long idVinculo) {
		return this.vinculoBTIServico.obterPercentualConclusao(idVinculo);
	}

}
