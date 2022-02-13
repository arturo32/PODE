package br.ufrn.imd.app3.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.pode.controle.VinculoControlador;
import br.ufrn.imd.pode.servico.GenericoServico;

import br.ufrn.imd.app3.modelo.VinculoPlataforma;
import br.ufrn.imd.app3.modelo.dto.VinculoPlataformaDTO;
import br.ufrn.imd.app3.servico.VinculoPlataformaServico;

@RestController
@RequestMapping("/vinculos")
public class VinculoPlataformaControlador extends VinculoControlador<VinculoPlataforma, VinculoPlataformaDTO> {

	private VinculoPlataformaServico vinculoPlataformaServico;

	@Autowired
	public void setVinculoBTIServico(VinculoPlataformaServico vinculoPlataformaServico) {
		this.vinculoPlataformaServico = vinculoPlataformaServico;
	}

	@Override
	protected GenericoServico<VinculoPlataforma, VinculoPlataformaDTO, Long> servico() {
		return this.vinculoPlataformaServico;
	}
}
